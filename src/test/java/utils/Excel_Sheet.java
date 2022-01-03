package utils;
// JAVA
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// EXCEL
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Sheet {

    private static Excel_Sheet EXCEL_SHEET;

    private final XSSFSheet sheet;
    private final XSSFRow headers;
    private final Map<String, Map<String, String>> mappedSheet;

    private Excel_Sheet(int sheetIndex, String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheetAt(sheetIndex);
        headers = sheet.getRow(0);
        mappedSheet = this.mapSheet();
    }

    public static Excel_Sheet getExcelSheet(int sheetIndex, String filePath) throws IOException {
        if (EXCEL_SHEET == null) EXCEL_SHEET = new Excel_Sheet(sheetIndex, filePath);
        return EXCEL_SHEET;
    }

    /**
     * @return {
     * "Page-Name" {
     * "Column-Header": "Value",
     * "Column-Header": "Value"
     * },
     * "Page-Name" {
     * "Column-Header": "Value",
     * "Column-Header": "Value"
     * }
     * }
     */
    public Map<String, Map<String, String>> mapSheet() {
        Map<String, Map<String, String>> mappedSheet = new HashMap<>();

        // iterate sheet's rows
        for (int row = 1; row <= this.sheet.getLastRowNum(); row++) {

            XSSFRow currentRow = sheet.getRow(row);
            Map<String, String> columnNameAndValue = new HashMap<>();

            for (int column = 1; column < currentRow.getLastCellNum(); column++) {
                String header = headers.getCell(column).toString();
                String rowValue = sheet.getRow(row).getCell(column).toString();
                String pageName = sheet.getRow(row).getCell(0).toString();

                columnNameAndValue.put(header, rowValue);
                mappedSheet.put(pageName, columnNameAndValue);

            }
        }
        return mappedSheet;
    }

    public Map<String, Map<String, String>> getMappedSheet() {
        return mappedSheet;
    }

    /**
     * @param pageName <ol>
     *                 <li>Customer-Login</li>
     *                 <li>Customer-Withdraw</li>
     *                 <li>Customer-Deposit</li>
     *                 <li>Manager-AddCustomer</li>
     *                 </ol>
     * @return
     */
    public Map<String, Map<String, String>> getPageData(int pageName) {
        Map<String, Map<String, String>> result = new HashMap<>();

        String page = null;

        switch (pageName) {
            case 1:
                page = "Customer-Login";
                break;
            case 2:
                page = "Customer-Withdraw";
                break;
            case 3:
                page = "Customer-Deposit";
                break;
            case 4:
                page = "Manager-AddCustomer";
                break;
            case 5:
                page = "Customer-Transactions";
                break;
            default:
                page = "null";
                break;
        }

        for (Map.Entry<String, Map<String, String>> sheetRow : this.mappedSheet.entrySet()) {
            if (sheetRow.getKey().contains(page)) {
                result.put(sheetRow.getKey(), sheetRow.getValue());
            }
        }

        return result;
    }
}
