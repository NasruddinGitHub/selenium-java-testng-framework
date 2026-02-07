package com.framework.ui.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.framework.ui.automation.exceptions.UiFrameworkException;

public final class ExcelUtil {
	private static final String EXCEL_PATH =
            System.getProperty("user.dir") + "/src/test/resources/test-data/sit-test-data.xlsx";

    // Each thread gets its own Workbook
    private static ThreadLocal<Workbook> workbookTL = new ThreadLocal<>();

    private ExcelUtil() {
    }

    // Load workbook for current thread (lazy initialization)
    private static Workbook getWorkbook() {
        if (workbookTL.get() == null) {
            try {
                workbookTL.set(new XSSFWorkbook(new FileInputStream(EXCEL_PATH)));
            } catch (IOException e) {
                throw new UiFrameworkException("Unable to load Excel file", e);
            }
        }
        return workbookTL.get();
    }

    public static String getData(String sheetName, String testCase, int columnIndex) {

        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            Cell tcCell = row.getCell(0);
            if (formatter.formatCellValue(tcCell).equalsIgnoreCase(testCase)) {
                return formatter.formatCellValue(row.getCell(columnIndex));
            }
        }
        throw new UiFrameworkException("Test case not found: " + testCase);
    }

    // Call once after suite / thread execution
    public static void closeExcel() {
        try {
            if (workbookTL.get() != null) {
                workbookTL.get().close();
            }
        } catch (IOException e) {
            throw new UiFrameworkException("Failed to close Excel", e);
        } finally {
            workbookTL.remove();
        }
    }
}
