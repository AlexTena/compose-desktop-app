import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun EmployeeTimeDisplay(
    employees: ArrayList<Employee>,
    modifier: Modifier
) {

    var list by remember { mutableStateOf(employees) }

    Column (
        modifier = modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = "Los empleados con recuadros de color rojo significa que checaron solo entrada " +
                    "y no la salida en por lo menos un dia. Los botones de restar 2 horas y 1 hora restan " +
                    "esas horas por cada dia trabajado para el caso de los empleados con horas para comida.",
            maxLines = 4,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list.size) { i ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        border = if(list[i].hasWarning) { BorderStroke(2.dp, Color.Red) } else { BorderStroke(2.dp, Color.Black) },
                        backgroundColor = if(list[i].hasWarning) { Color.Red } else { Color.White },
                    ) {
                        Column (verticalArrangement = Arrangement.SpaceAround) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center,
                                text = "${list[i].name} | Horas totales: ${if(list[i].displayedHours > 0) list[i].displayedHours else 0 }" +
                                        " | Horas extras: ${if(list[i].displayedExtra > 0) list[i].displayedExtra else 0 }",
                                color = if(list[i].hasWarning) { Color.White } else { Color.Black },
                                fontSize = 16.sp,
                                maxLines = 4,
                            )
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = list[i].notes.joinToString("\n"),
                                textAlign = TextAlign.Start,
                                color = if(list[i].hasWarning) { Color.White } else { Color.Black },
                                fontSize = 14.sp,
                                maxLines = 4,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                list = list.mapIndexed { j, item ->
                                    if(i == j) {
                                        item.copy(
                                            displayedHours = item.hours,
                                            displayedExtra = item.extraTime
                                        )
                                    } else item
                                } as ArrayList<Employee> },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        ) {
                            Text(
                                text = "Revertir",
                                color = Color.White,
                                fontSize = 12.sp)
                        }
                        Button(
                            onClick = {
                                list = list.mapIndexed { j, item ->
                                    if(i == j) {
                                        item.copy(
                                            displayedHours = item.hours - (item.dayCount * 2),
                                            displayedExtra = item.extraTime - (item.dayCount * 2)
                                        )
                                    } else item
                                } as ArrayList<Employee> },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        ) {
                            Text(
                                text = "Restar 2 horas",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }

                        Button(
                            onClick = {
                                list = list.mapIndexed { j, item ->
                                    if(i == j) {
                                        item.copy(
                                            displayedHours = item.hours - item.dayCount,
                                            displayedExtra = item.extraTime - item.dayCount
                                        )
                                    } else item
                                } as ArrayList<Employee> },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        ) {
                            Text(
                                text = "Restar 1 hora",
                                color = Color.White,
                                fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}