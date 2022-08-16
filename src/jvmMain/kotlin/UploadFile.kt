import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.io.File

class UploadFile {
    var fileName by mutableStateOf("")
    var employees by mutableStateOf(ArrayList<Employee>())
    var isActive by mutableStateOf(false)
    var isFileLoaded by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    private lateinit var file: File
    private var coroutineScope = CoroutineScope(Dispatchers.IO)


    fun start() {
        file = openFileDialog(ComposeWindow(), "Choose File", listOf(".xlsx", ".xls"))
        fileName = file.name
        isFileLoaded = true
    }

    fun calculateHours() {
        coroutineScope.launch {
            isLoading = true
            val excelReader = ExcelReader()
            employees = excelReader.readExcelFile(file.absolutePath.replace("\\", "/"))
            isLoading = false
            isActive = true
        }
    }

    private fun openFileDialog(window: ComposeWindow, title: String, allowedExtensions: List<String>, allowMultiSelection: Boolean = false): File {
        return try {
            FileDialog(window, title, FileDialog.LOAD).apply {
                isMultipleMode = allowMultiSelection

                // windows
                file = allowedExtensions.joinToString(";") { "*$it" } // e.g. '*.jpg'

                // linux
                setFilenameFilter { _, name ->
                    allowedExtensions.any {
                        name.endsWith(it)
                    }
                }
                isVisible = true
            }.files[0]
        } catch (e: Exception) {
            File("")
        }
    }

}