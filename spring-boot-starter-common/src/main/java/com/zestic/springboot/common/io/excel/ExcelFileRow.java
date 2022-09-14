package com.zestic.springboot.common.io.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class ExcelFileRow implements Consumer<Row> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelFileRow.class);

    private List<List<String>> list = new ArrayList<>();

    public ExcelFileRow() {
    }

    @Override
    public void accept(Row row) {
        try {
            logger.debug("Row number [" + row.getRowNum() + "]");
            if (row.getRowNum() == 0) {
                return;
            }
            ExcelFileColumn column = new ExcelFileColumn();
            row.forEach(column);
            list.add(column.getList());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }
}
