package in.zestic.common.io.excel;

import in.zestic.common.io.FileParser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelFileParser implements FileParser {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelFileParser.class);

    public List<List<String>> parse(final File file) {
        List<List<String>> rows = null;
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        try {
            fileInputStream = new FileInputStream(file);
            if (file.getName().toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (file.getName().toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            }
            if (workbook != null) {
                Sheet sheet = workbook.getSheetAt(0);
                ExcelFileRow row = new ExcelFileRow();
                sheet.forEach(row);
                rows = row.getList();
                logger.info(Arrays.toString(row.getList().toArray()));
            } else {
                logger.warn("Unable to open workbook please retry.");
            }
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            try {
                fileInputStream.close();
                workbook.close();
            } catch (IOException ex) {
                logger.error("Error closing workbook ", ex);
            }
        }
        return rows;
    }
}
