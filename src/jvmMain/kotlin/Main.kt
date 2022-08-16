// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
@Preview
fun App() {
    val fileUploader = remember { UploadFile() }
    MaterialTheme {
        when {
            fileUploader.isLoading -> {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(70.dp),
                        color = Color.Red
                    )
                }
            }
            !fileUploader.isActive && !fileUploader.isLoading -> {
                UploadFileDisplay(
                    fileUploader.fileName,
                    fileUploader::start,
                    fileUploader::calculateHours,
                    fileUploader.isFileLoaded,
                    Modifier.fillMaxSize()
                )
            }
            fileUploader.isActive -> {
                EmployeeTimeDisplay(
                    fileUploader.employees,
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Calculadora de Horas Extras") {
        App()
    }
}

