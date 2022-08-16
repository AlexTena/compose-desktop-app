import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun UploadFileDisplay(
    fileName: String,
    onUploadFile: () -> Unit,
    onCalculateHours: () -> Unit,
    isFileLoaded: Boolean,
    modifier: Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Calculadora de Horas Extras",
            fontSize = 36.sp
        )
        Spacer(Modifier.height(60.dp))

        Card(
            border = BorderStroke(1.dp, Color.Black)) {
            Text(
                modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
                .width(420.dp),
                text = "Archivo: $fileName",
                fontSize = 26.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.height(32.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.padding(1.dp),
                onClick = onUploadFile,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(
                    text = "Cargar Archivo",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
            Spacer(Modifier.width(16.dp))
            Button(
                modifier = Modifier.padding(1.dp),
                onClick = onCalculateHours,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                enabled = isFileLoaded && fileName.isNotBlank()
            ) {
                Text(
                    text = "Calcular Horas",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }

        }
    }

}