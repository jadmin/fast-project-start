package #{packagePrefix}#.utils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelExportHandler {

    private static Logger logger = LoggerFactory.getLogger(ExcelExportHandler.class);

    public static void export(String name, String sheetName, String[] titles, List<Object[]> data, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        if (name == null) {
            throw new Exception("导出文件名不能为空.");
        }
        if (titles == null || titles.length == 0) {
            throw new Exception("表头标题为空.");
        }
        if (!name.endsWith(".xls")) {
            name += ".xls";
        }
        if (data == null) {
            data = new ArrayList<Object[]>(0);
        }
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        OutputStream out = null;
        try {
            String filename = null;
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(name, "UTF-8");
            } else {
                filename = new String(name.getBytes(), "ISO8859-1");
            }

            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            ExcelExportUtils utils = new ExcelExportUtils(titles);
            HSSFWorkbook book = utils.makeObjectToExcel(data, sheetName != null ? sheetName : "数据");
            out = response.getOutputStream();
            book.write(out);
        } catch (IOException e) {
            logger.error("写入Excel异常!", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("关闭流异常!", e);
            }
        }
    }

}
