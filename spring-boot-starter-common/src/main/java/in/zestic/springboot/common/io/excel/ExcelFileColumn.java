package in.zestic.springboot.common.io.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class ExcelFileColumn implements Consumer<Cell> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelFileColumn.class);

    private List<String> list = new ArrayList<>();

    public ExcelFileColumn() {
    }

    @Override
    public void accept(Cell cell) {
        String data = null;
        try {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    logger.info("Column index [" + cell.getColumnIndex() + "] string value [" + cell.getStringCellValue().trim() + "]");
                    data = cell.getStringCellValue().trim();
                    break;
                case NUMERIC:
                    logger.info("Column index [" + cell.getColumnIndex() + "] numeric value [" + cell.getNumericCellValue() + "]");
                    data = Double.toString(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    logger.info("Column index [" + cell.getColumnIndex() + "] boolean value [" + cell.getBooleanCellValue() + "]");
                    data = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    logger.info("Column index [" + cell.getColumnIndex() + "] formula value [" + cell.getCellFormula().trim() + "]");
                    break;
                default:
                    logger.info("Column index [" + cell.getColumnIndex() + "] value [Unidentified]");
            }
            list.add(data);
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }
}
