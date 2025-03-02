package core.service

import core.AppState
import core.models.Result
import core.security.SecurityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.jetbrains.exposed.sql.transactions.transaction
import org.sqlite.util.Logger
import org.sqlite.util.LoggerFactory
import repository.creditcard.CreditCard
import repository.password.Password
import java.io.ByteArrayInputStream
import java.time.LocalDateTime

class ExcelImportService(
    private val appState: AppState,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val logger: Logger = LoggerFactory.getLogger(ExcelImportService::class.java)
) {

    suspend fun importExcel(excel: ByteArray): Result<Unit> = withContext(dispatcher) {
        try {
            val workbook = XSSFWorkbook(ByteArrayInputStream(excel))

            val passwordSheet = workbook.getSheet("Passwords")
            val creditCardSheet = workbook.getSheet("Credit Cards")

            if (passwordSheet == null || creditCardSheet == null) {
                return@withContext Result.Error("Invalid Excel Template.")
            }

            transaction {
                importPasswords(passwordSheet)
                importCreditCards(creditCardSheet)
            }

            workbook.close()
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error("Failed to import Excel: ${e.message}")
        }
    }

    private fun importPasswords(sheet: Sheet) {
        val header = sheet.getRow(0)?.map { it.stringCellValue } ?: return

        sheet.drop(1).forEach { row ->
            val name = row.getCellValue(header.indexOf("Name"))
            val username = row.getCellValue(header.indexOf("Username"))
            val email = row.getCellValue(header.indexOf("Email"))
            val encryptedPassword = row.getCellValue(header.indexOf("Password"))?.let { appState.encryptString(it) }
            val website = row.getCellValue(header.indexOf("Website"))
            val favorite = row.getCellValue(header.indexOf("Favorite"))?.toBoolean() ?: false
            val deleted = row.getCellValue(header.indexOf("Deleted"))?.toBoolean() ?: false

            if (!name.isNullOrBlank() && encryptedPassword != null) {
                Password.new {
                    this.user = SecurityContext.authenticatedUser!!
                    this.name = name
                    this.username = username
                    this.email = email
                    this.password = encryptedPassword
                    this.website = website
                    this.favorite = favorite
                    this.deleted = deleted
                    this.creationDateTime = LocalDateTime.now()
                    this.createdBy = SecurityContext.authenticatedUser?.userName!!
                    this.lastUpdateDateTime = LocalDateTime.now()
                    this.lastUpdatedBy = SecurityContext.authenticatedUser?.userName!!
                    this.version = 1
                }
            }
        }
    }

    private fun importCreditCards(sheet: Sheet) {
        val header = sheet.getRow(0)?.map { it.stringCellValue } ?: return

        sheet.drop(1).forEach { row ->
            val name = row.getCellValue(header.indexOf("Name"))
            val owner = SecurityContext.authenticatedUser!!
            val number = row.getCellValue(header.indexOf("Number"))
            val cvc = row.getCellValue(header.indexOf("CVC"))?.let { appState.encryptString(it) }
            val pin = row.getCellValue(header.indexOf("PIN"))?.let { appState.encryptString(it) }
            val expiryDate = row.getCellValue(header.indexOf("Expiry Date"))
            val notes = row.getCellValue(header.indexOf("Notes"))
            val favorite = row.getCellValue(header.indexOf("Favorite"))?.toBoolean() ?: false
            val deleted = row.getCellValue(header.indexOf("Deleted"))?.toBoolean() ?: false

            if (!name.isNullOrBlank() && !number.isNullOrBlank() && cvc != null) {
                CreditCard.new {
                    this.user = owner
                    this.owner = owner
                    this.name = name
                    this.number = number
                    this.cvc = cvc
                    this.pin = pin
                    this.expiryDate = expiryDate!!
                    this.notes = notes
                    this.favorite = favorite
                    this.deleted = deleted
                    this.creationDateTime = LocalDateTime.now()
                    this.createdBy = SecurityContext.authenticatedUser?.userName!!
                    this.lastUpdateDateTime = LocalDateTime.now()
                    this.lastUpdatedBy = SecurityContext.authenticatedUser?.userName!!
                    this.version = 1
                }
            }
        }
    }

    private fun Row.getCellValue(index: Int): String? {
        if (index < 0 || index >= this.physicalNumberOfCells) return null

        val cell = this.getCell(index) ?: return null

        return when (cell.cellType) {
            CellType.STRING -> cell.stringCellValue.trim()
            CellType.NUMERIC -> cell.numericCellValue.toLong().toString()
            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            CellType.BLANK -> null
            else -> null
        }
    }


}