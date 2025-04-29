package utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Map;

public class DataProviders {


    // For Dynamic data from Excel File
    @DataProvider(name = "LoginData")
    public static Object[][] getLoginData() throws IOException {

        String filePath = System.getProperty("user.dir") + "/TestData/TestData.xlsx";
        ExcelUtils.LoadExcel(filePath, "Sheet1");
        int rowCount = ExcelUtils.getRowCount();

        // Count how many valid rows have data
        int validRowCount = 0;
        for (int i = 1; i < rowCount; i++) {
            Map<String, String> rowData = ExcelUtils.getRowData(i);
            if (!rowData.isEmpty()) {
                validRowCount++;
            }
        }

        // Create data array based on valid rows
        Object[][] data = new Object[validRowCount][1];
        int dataIndex = 0;

        for (int i = 1; i < rowCount; i++) {
            Map<String, String> rowData = ExcelUtils.getRowData(i);
            if (!rowData.isEmpty()) {
                data[dataIndex][0] = rowData;
                dataIndex++;
            }
        }

        ExcelUtils.CloseExcel();
        return data;
    }

    //  For Small data for quick test
    @DataProvider(name = "LoginData2")
    public static Object[][] getStaticLoginData() {

        return new Object[][] {
                { "admin@yourstore.com", "admin" }
        };
    }
}
