package org.fzsoft.shoppingmall.utils.excel;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yhj on 17/1/11.
 */
public class ExcelNewHelper {

    private final int pageSize = 100;

    private final String pageSizeName = "pageSize";
    private final String offsetName = "offset";

    SqlSessionFactory sqlSessionFactory;


    public ExcelWriteBuilder createExcelWriteBuilder(String name) {

        return ExcelWriteBuilder.createExcelNewHelper(name);
    }


    public void queryAndWrite(String sqlmap, Map<String, Object> param, ExcelWriteBuilder builder) {

        internalWrite(sqlmap, param, offsetName, pageSizeName, builder);
    }

    public void queryAndWrite(String sqlmap, Map<String, Object> param, String offsetName, String pageSizeName, ExcelWriteBuilder builder) {

        if (StringUtils.isEmpty(offsetName)) {
            offsetName = this.offsetName;
        }


        if (StringUtils.isEmpty(pageSizeName)) {
            pageSizeName = this.pageSizeName;
        }

        internalWrite(sqlmap, param, offsetName, pageSizeName, builder);
    }


    private void internalWrite(String sqlmap, Map<String, Object> param, String offsetName, String pageSizeName, ExcelWriteBuilder builder) {

        int page = 1;
        while (true) {

            param.put(pageSizeName, pageSize);
            param.put(offsetName, (page - 1) * pageSize);

            int writeSize = selectAndWrite(sqlmap, param, builder);

            if (writeSize < pageSize) { // 如果上次返回的数据不满一页,表示没有了下一页的数据.
                break;
            }

            page++;

        }

    }


    /**
     * @param sqlmap
     * @param param
     * @param builder
     * @Result
     */
    public int selectAndWrite(String sqlmap, Map<String, Object> param, ExcelWriteBuilder builder) {

        SqlSession sqlSession = null;
        final AtomicInteger writeSize = new AtomicInteger(0);
        List<Object> list = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            list =  sqlSession.selectList(sqlmap,param) ;

            if(list != null) {
                for (Object o : list) {
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(o);

                    builder.appendRowData(jsonObject);
                }

            }
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }

        return list != null ? list.size() : 0;
    }


    public void write2Servlet(HttpServletResponse response, String str, ExcelWriteBuilder builder)
            throws IOException {

        OutputStream out = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((str + ".xls").getBytes(), "iso-8859-1"));

            out = response.getOutputStream();
            builder.writeTo(out);
        } finally {
            if (out != null)
                out.close();
        }

    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
