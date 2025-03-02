package core.models.enumeration

enum class FileExtensions(val filter: String, val fileExtension: String) {
    JSON("*.json", "json"),
    XLSX("*.xlsx", "xlsx"),
}