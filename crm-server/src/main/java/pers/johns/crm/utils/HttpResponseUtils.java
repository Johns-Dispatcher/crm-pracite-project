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

    /**
     * 向前端发送 JSON 数据
     * @param response {@link HttpServletResponse} 响应对象
     * @param jsonData JSON 串
     * @throws IOException I/O 异常
     */
    public static void responseJson(HttpServletResponse response, String jsonData)
            throws IOException {
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        // 设置响应类型
        response.setContentType("application/json;charset=utf-8");

        // 输出流
        PrintWriter out = response.getWriter();
        out.print(jsonData);
        out.flush();
    }
}
