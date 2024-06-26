package pers.johns.crm.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassName    : HttpResponseUtils
 * <br/>
 * Description  : 处理 HTTP 响应的工具类
 * <br/>
 * CreateTime   : 2024/6/26 11:09
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class HttpResponseUtils {
    private HttpResponseUtils() {};

    public static void responseJson(HttpServletResponse response, String jsonData) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        out.print(jsonData);
        out.flush();
    }
}
