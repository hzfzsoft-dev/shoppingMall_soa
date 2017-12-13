package org.fzsoft.shoppingmall.utils.excel;

import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fzsoft.shoppingmall.utils.excel.entry.DataType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yhj on 17/1/11.
 */
public class ExcelWriteBuilder {

    private Logger logger = LoggerFactory.getLogger(ExcelWriteBuilder.class);

    private Workbook workbook;
    private Sheet sheet;
    private Row headerRow;
    private int currentRow = 0;
    private List<Column> columnList = new ArrayList<>();

    private Map<String,Object> data=new LinkedHashMap<> ();

    public void setData(String key,Object value) {
        data.put(key,value);
    }

    private ExcelWriteBuilder(String name) {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(name);
        headerRow = sheet.createRow((short) currentRow);
        ++currentRow;
    }

    /**
     * @param name
     * @return
     */
    public static ExcelWriteBuilder createExcelNewHelper(String name) {
        return new ExcelWriteBuilder(name);
    }

    /**
     * @return
     */
    public CellStyle createCellStyle() {

        return workbook.createCellStyle();
    }

    /**
     *
     * @param format
     * @param alignment
     * @param font
     * @return
     */
    public CellStyle createCellStyle(String format, short alignment,Font font) {
        CellStyle cs2 = createCellStyle();

        cs2.setDataFormat(createDataFormat(format));
        cs2.setAlignment(alignment);
        cs2.setFont(font);

        return cs2;
    }

    /**
     * @param fontHeightInPoints
     * @param color
     * @return
     */
    public Font createFont(String fontName, short fontHeightInPoints, short color, short boldweight) {

        Font font = workbook.createFont();

        // 创建第一种字体样式
        font.setFontHeightInPoints(fontHeightInPoints);
        font.setColor(color);
        font.setBoldweight(boldweight);
        font.setFontName(fontName);

        return font;
    }


    /**
     * @return
     */
    public short createDataFormat(String name) {
        return workbook.createDataFormat().getFormat(name);
    }

    /**
     * @param name
     * @param expression
     * @return
     */
    public ExcelWriteBuilder addHeaderColumn(String name, String expression) {

        return addHeaderColumn(name, expression, (short) 0, null, null,null);
    }


    /**
     *
     * @param name
     * @param expression
     * @param cellStyle
     * @return
     */
    public ExcelWriteBuilder addHeaderColumn(String name, String expression, CellStyle cellStyle) {
        return addHeaderColumn(name, expression, (short) 0, null, cellStyle,cellStyle);
    }

    /**
     *
     * @param name
     * @param expression
     * @param type
     * @param cellStyle
     * @return
     */
    public ExcelWriteBuilder addHeaderColumn(String name, String expression, DataType type, CellStyle cellStyle) {
        return addHeaderColumn(name, expression, (short) 0, type, cellStyle,cellStyle);
    }


    /**
     *
     * @param name
     * @param expression
     * @param width
     * @param type
     * @param cellStyle
     * @return
     */
    public ExcelWriteBuilder addHeaderColumn(String name, String expression, short width, DataType type, CellStyle cellStyle) {
        return addHeaderColumn(name, expression, width, type, cellStyle,cellStyle);
    }


    /**
     * @param name
     * @param expression
     * @param width
     * @param type
     * @param headerCellStyle
     * @param columnCellStyle
     * @return
     */
    public ExcelWriteBuilder addHeaderColumn(String name, String expression, short width, DataType type, CellStyle headerCellStyle, CellStyle columnCellStyle) {
        int index = columnList.size();
        columnList.add(new Column(expression, type, columnCellStyle));
        if (width > 0) {
            sheet.setColumnWidth((short) index, width);
        }

        Cell cell = headerRow.createCell(index);

        cell.setCellValue(name);

        if (headerCellStyle != null) {
            cell.setCellStyle(headerCellStyle);
        }

        return this;
    }


    /**
     * @param map
     */
    public void appendRowData(Map<String, Object> map) {

        Row row = sheet.createRow(currentRow++);


        for (int i = 0; i < columnList.size(); i++) {

            Cell cell = row.createCell(i);
            Column column = columnList.get(i);
            data.forEach((key, value) -> {
                map.put(key,value);
            });
            try {
                Object obj = AviatorEvaluator.execute(column.expression, map);

                if(obj != null) {
                    if (column.type != null) {
                        if (DataType.INTEGER == column.type) {
                            BigDecimal bigDecimal =  new BigDecimal(String.valueOf(obj));
                            cell.setCellValue(bigDecimal.intValue());
                        } else if (DataType.DOUBLE == column.type) {
                            BigDecimal bigDecimal =  new BigDecimal(String.valueOf(obj));
                            cell.setCellValue(bigDecimal.doubleValue());
                        } else if (DataType.DATE == column.type) {

                            if(obj instanceof Date){
                                cell.setCellValue((Date) obj);
                            }else if (StringUtils.isNumeric(String.valueOf(obj))) {
                                Calendar calendar =   Calendar.getInstance();
                                calendar.setTime(new Date(Long.parseLong(String.valueOf(obj))));
                            }else{
                                cell.setCellValue(String.valueOf(obj));
                            }

                        } else if (DataType.RICHTEXT == column.type) {
                            cell.setCellValue(new HSSFRichTextString(String.valueOf(obj)));
                        } else {
                            cell.setCellValue(String.valueOf(obj));
                        }
                    } else {
                        if (obj instanceof Integer) {
                            cell.setCellValue((Integer) obj);
                        } else if (obj instanceof Double) {
                            cell.setCellValue((Double) obj);
                        } else if (obj instanceof Date) {
                            cell.setCellValue((Date) obj);
                        } else {
                            cell.setCellValue(String.valueOf(obj));
                        }
                    }
                }

            } catch (Exception e) {
                logger.warn("导出{}转换异常:{}",column.toString(),e.getMessage());
            }

            if (column.cellStyle != null) {
                cell.setCellStyle(column.cellStyle);
            }

        }

    }


    /**
     * @return
     * @throws IOException
     */
    public byte[] getContext() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        return os.toByteArray();
    }

    /**
     * @param os
     * @throws IOException
     */
    public void writeTo(OutputStream os) throws IOException {

        workbook.write(os);
    }


    /**
     * @return
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    public void clear() {
        workbook = null;
        sheet = null;
    }

    class Column {
        String expression;
        DataType type;
        CellStyle cellStyle;

        public Column(String expression, DataType type, CellStyle cellStyle) {
            this.expression = expression;
            this.type = type;
            this.cellStyle = cellStyle;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "expression='" + expression + '\'' +
                    ", type=" + type +
                    ", cellStyle=" + cellStyle +
                    '}';
        }
    }

}
