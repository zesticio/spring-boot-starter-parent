package in.zestic.springboot.common.io.excel;

import java.io.File;

public class TestParseExcelFile {

    public static void main(String[] args) {
        ExcelFileParser parser = new ExcelFileParser();
        parser.parse(new File("countries.xlsx"));
    }
}
