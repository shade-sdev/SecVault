package core.service

import core.AppState
import core.models.Result
import core.models.dto.ExcelExportResult
import core.security.SecurityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import repository.creditcard.CreditCard
import repository.creditcard.CreditCardRepository
import repository.password.Password
import repository.password.PasswordRepository
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ExcelExportService(
    private val passwordRepository: PasswordRepository,
    private val creditCardRepository: CreditCardRepository,
    private val appState: AppState
) {

    suspend fun exportExcel(): Result<ExcelExportResult> = withContext(Dispatchers.IO) {
        if (SecurityContext.getGoogleCredential == null) {
            return@withContext Result.Error("Google Integration not setup.")
        }

        val passwordResult = passwordRepository.findAllByUserId(SecurityContext.getUserId!!)
        val creditCardResult = creditCardRepository.findAllByUserId(SecurityContext.getUserId!!)

        if (passwordResult is Result.Success && creditCardResult is Result.Success) {
            val workbook = XSSFWorkbook()
            createPasswordSheet(workbook, passwordResult.data)
            createCreditCardSheet(workbook, creditCardResult.data)

            val fileOutputStream = ByteArrayOutputStream()
            workbook.write(fileOutputStream)
            workbook.close()

            val byteArray = fileOutputStream.toByteArray()
            val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            return@withContext Result.Success(ExcelExportResult("SecVault_Export_$dateTime.xlsx", byteArray))
        } else {
            if (passwordResult is Result.Error) {
                return@withContext Result.Error("Error fetching passwords: ${passwordResult.message}")
            }
            if (creditCardResult is Result.Error) {
                return@withContext Result.Error("Error fetching credit cards: ${creditCardResult.message}")
            }
        }

        return@withContext Result.Error("Export failed.")
    }

    private fun createPasswordSheet(workbook: XSSFWorkbook, passwords: List<Password>): Sheet {
        val passwordSheet = workbook.createSheet("Passwords")
        val passwordHeader = listOf(
            "Name", "Username", "Email", "Password", "Website", "Favorite", "Deleted",
            "Creation Date", "Created By", "Last Update Date", "Last Updated By", "Version"
        )

        createSheet(passwordSheet, passwordHeader, passwords.map { password ->
            listOf(
                password.name,
                password.username,
                password.email,
                appState.decryptPassword(password.password),
                password.website,
                password.favorite.toString(),
                password.deleted.toString(),
                password.creationDateTime.toString(),
                password.createdBy,
                password.lastUpdateDateTime.toString(),
                password.lastUpdatedBy,
                password.version.toString()
            )
        })

        return passwordSheet
    }

    private fun createCreditCardSheet(workbook: XSSFWorkbook, creditCards: List<CreditCard>): Sheet {
        val creditCardSheet = workbook.createSheet("Credit Cards")
        val creditCardHeader = listOf(
            "Name", "Owner", "Number", "CVC", "PIN", "Expiry Date", "Notes", "Favorite", "Deleted",
            "Creation Date", "Created By", "Last Update Date", "Last Updated By", "Version"
        )

        createSheet(creditCardSheet, creditCardHeader, creditCards.map { card ->
            listOf(
                card.name,
                card.owner.userName,
                card.number,
                appState.decryptPassword(card.cvc),
                appState.decryptPassword(card.pin),
                card.expiryDate,
                card.notes,
                card.favorite.toString(),
                card.deleted.toString(),
                card.creationDateTime.toString(),
                card.createdBy,
                card.lastUpdateDateTime.toString(),
                card.lastUpdatedBy,
                card.version.toString()
            )
        })

        return creditCardSheet
    }

    private fun createSheet(
        sheet: Sheet,
        header: List<String>,
        data: List<List<String?>>
    ) {
        val headerRow: Row = sheet.createRow(0)
        header.forEachIndexed { index, column ->
            val cell: Cell = headerRow.createCell(index)
            cell.setCellValue(column)
        }

        data.forEachIndexed { rowIndex, rowData ->
            val row: Row = sheet.createRow(rowIndex + 1)
            rowData.forEachIndexed { colIndex, cellData ->
                val cell: Cell = row.createCell(colIndex)
                cell.setCellValue(cellData ?: "")
            }
        }
    }

}