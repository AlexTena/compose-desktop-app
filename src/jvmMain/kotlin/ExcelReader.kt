import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

import org.apache.poi.ss.usermodel.*;
import java.io.*
import java.util.regex.Pattern


class ExcelReader {

    fun readExcelFile(filepath: String) : ArrayList<Employee> {
        val employees = ArrayList<Employee>()
        var sum = 0
        var count = 0

        try {
            var currentName = ""
            val p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]")
            val excelFile = FileInputStream(filepath)
            val workbook: Workbook = WorkbookFactory.create(excelFile)
            val datatypeSheet: Sheet = try {
                workbook.getSheetAt(1)
            } catch (e: Exception) {
                workbook.getSheetAt(0)
            }
            val iterator: Iterator<Row> = datatypeSheet.iterator()

            while (iterator.hasNext()) {
                val currentRow: Row = iterator.next()
                val cellIterator: Iterator<Cell> = currentRow.iterator()
                while (cellIterator.hasNext()) {
                    val currentCell: Cell = cellIterator.next()
                    if (currentCell.cellType === CellType.STRING ) {
                        val value = currentCell.stringCellValue
                        if(value.contains("Nombre")) {
                            sum = 0
                            count = 0
                            currentName = datatypeSheet.getRow(currentRow.rowNum).getCell(9).stringCellValue
                            employees.add(
                                Employee(
                                    name = currentName,
                                    displayedHours = sum,
                                    displayedExtra = sum,
                                    hours = sum,
                                    hasWarning = false,
                                    dayCount = count,
                                    extraTime = sum,
                                    notes = ArrayList()
                                )
                            )
                        }
                        //Check for cells containing 24 hours format strings
                        if(p.matcher(value).find()) {
                            val timesList = ArrayList<String>(value.chunked(6))
                            val firstHours = timesList.first().substringBefore(":").trim().toInt()
                            val firstMinutes = timesList.first().substringAfter(":").trim().toInt()
                            var lastHours = timesList.last().substringBefore(":").trim().toInt()
                            val lastMinutes = timesList.last().substringAfter(":").trim().toInt()

                            count++

                            if(firstMinutes in 11 .. 59 && lastMinutes < 50) {
                                lastHours--
                            }

                            if(lastMinutes >= 50 && firstMinutes <= 10) {
                                lastHours++
                            }

                            sum += (lastHours - firstHours)

                            employees.find {
                                it.name == currentName
                            }?.apply {
                                hours = sum
                                displayedHours = sum

                                if((lastHours - firstHours) > 8) {
                                    extraTime += ((lastHours - firstHours) - 8)
                                    displayedExtra = extraTime
                                }
                                if(lastHours - firstHours < 8 && timesList.size > 1) {
                                    val day = datatypeSheet
                                        .getRow(currentRow.rowNum - 2)
                                        .getCell(currentCell.columnIndex)
                                        .numericCellValue
                                        .toInt()
                                    notes.add("**El dia $day se trabajo menos de 8 horas**")
                                }
                                if(timesList.size == 1) {
                                    hasWarning = true
                                    val day = datatypeSheet
                                        .getRow(currentRow.rowNum - 2)
                                        .getCell(currentCell.columnIndex)
                                        .numericCellValue
                                        .toInt()
                                    notes.add("**El dia $day solo se checo la entrada**")
                                    count--
                                }
                                dayCount = count
                            }
                        }
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return employees
    }

}