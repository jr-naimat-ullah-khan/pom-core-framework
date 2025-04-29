package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

	private static Workbook workbook;
	private static Sheet sheet;
	private static boolean isWorkbookLoaded = false;

	// Load Excel File
	public static void LoadExcel(String filePath, String sheetName) throws IOException {
		if (!isWorkbookLoaded) {
			try (FileInputStream file = new FileInputStream(filePath)) {
				if (filePath.endsWith(".xlsx")) {
					workbook = new XSSFWorkbook(file);
				} else if (filePath.endsWith(".xls")) {
					workbook = new HSSFWorkbook(file);
				} else {
					throw new IllegalArgumentException("Unsupported file type.");
				}
				sheet = workbook.getSheet(sheetName);
				isWorkbookLoaded = true;
			} catch (IOException e) {
				System.out.println("Error loading Excel file: " + e.getMessage());
				throw e;
			}
		}
	}

	// Get Whole Row as Map<String, String> (Key = Column Name, Value = Cell Data)
	public static Map<String, String> getRowData(int rowIndex) {
		Map<String, String> rowData = new HashMap<>();
		Row headerRow = sheet.getRow(0);
		Row currentRow = sheet.getRow(rowIndex);

		if (currentRow == null) {
			return rowData; // Return empty map if the row is null
		}

		boolean isRowEmpty = true; // Flag to check if the row is empty

		// Iterate through all cells in the row
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String key = headerRow.getCell(i).getStringCellValue();
			String value = "";
			Cell cell = currentRow.getCell(i);

			if (cell != null) {
				if (cell.getCellType() == CellType.STRING) {
					value = cell.getStringCellValue();
				} else if (cell.getCellType() == CellType.NUMERIC) {
					value = String.valueOf(cell.getNumericCellValue());
				}

				// If there's any data in the cell, set isRowEmpty to false
				if (!value.isEmpty()) {
					isRowEmpty = false;
				}
			}

			rowData.put(key, value); // Add the key-value pair to the rowData map
		}

		// If the row is empty (no data in any cell), return an empty map
		if (isRowEmpty) {
			return new HashMap<>(); // Return an empty map if the row is empty
		}

		return rowData; // Return the populated map if the row contains data
	}


	// Get Total Row Count
	public static int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	// Close Excel
	public static void CloseExcel() throws IOException {
		if (workbook != null) {
			workbook.close();
			isWorkbookLoaded = false;
		}
	}
}
