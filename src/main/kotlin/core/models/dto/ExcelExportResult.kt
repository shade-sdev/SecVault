package core.models.dto

data class ExcelExportResult(
    val fileName: String,
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExcelExportResult

        if (fileName != other.fileName) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
