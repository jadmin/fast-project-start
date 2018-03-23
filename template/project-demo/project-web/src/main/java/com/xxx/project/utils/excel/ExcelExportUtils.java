package #{packagePrefix}#.utils.excel;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelExportUtils {

    private String dataFormat = "yyyy-MM-dd HH:mm:ss";
    private String[] title;

    public ExcelExportUtils() {
    }

    public ExcelExportUtils(String[] title) {
        this.title = title;
    }

    public HSSFWorkbook makeObjectToExcel(List<Object[]> dataList, String sheetname) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetname);
        createExcelHeader(sheet);
        createExcel(wb, sheet, dataList);
        return wb;
    }

    private void createExcelHeader(HSSFSheet sheet) {
        for (short i = 0; i < this.title.length; i = (short) (i + 1)) {
            setStringValue(sheet, (short) 0, i, this.title[i]);
        }
    }

    private void createExcel(HSSFWorkbook wb, HSSFSheet sheet, List<Object[]> dataList) {
        for (short i = 1; i <= dataList.size(); i = (short) (i + 1)) {
            Object[] object = (Object[]) dataList.get(i - 1);
            for (short j = 0; j < object.length; j = (short) (j + 1)) {
                doSetCell(wb, sheet, i, j, object[j]);
            }
        }
    }

    private void doSetCell(HSSFWorkbook wb, HSSFSheet sheet, short rowNum, short colNum, Object value) {
        if (value != null) {
            if ((value instanceof Number)) {
                setDoubleValue(sheet, rowNum, colNum, Double.valueOf(value.toString()));
            } else if ((value instanceof String)) {
                setStringValue(sheet, rowNum, colNum, value.toString());
            } else if ((value instanceof Date)) {
                HSSFCellStyle cellStyle = wb.createCellStyle();
                setDateValue(sheet, cellStyle, rowNum, colNum, (Date) value);
            }
        }
    }

    private void setDoubleValue(HSSFSheet sheet, short rowNum, short colNum, Double value) {
        HSSFCell cell = getMyCell(sheet, rowNum, colNum);
        cell.setCellType(0);
        cell.setCellValue(value.doubleValue());
    }

    private void setDateValue(HSSFSheet sheet, HSSFCellStyle cellStyle, short rowNum, short colNum, Date value) {
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(this.dataFormat));
        HSSFCell cell = getMyCell(sheet, rowNum, colNum);

        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    private void setStringValue(HSSFSheet sheet, short rowNum, short colNum, String value) {
        HSSFCell cell = getMyCell(sheet, rowNum, colNum);
        cell.setCellType(1);
        HSSFRichTextString str = new HSSFRichTextString(value);
        cell.setCellValue(str);
    }

    private HSSFCell getMyCell(HSSFSheet sheet, int rowNum, int colNum) {
        HSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        HSSFCell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        return cell;
    }

    public String getDataFormat() {
        return this.dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }
}
