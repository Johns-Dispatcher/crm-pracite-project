package pers.johns.crm.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName    : HttpResult
 * <br/>
 * Description  : 用于封装 HTTP 请求结果信息
 * <br/>
 * CreateTime   : 2024/6/26 10:01
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpResult implements Serializable {
    /**
     * 请求处理后的结果码
     * 200 = 正常响应
     * 500 = 错误响应
     */
    private Integer code;
    /**
     * 请求处理的简要信息
     */
    private String msg;
    /**
     *  响应携带数据
     */
    private Object data;

    public static HttpResult OK() {
        return OK(HttpResultCode.OK.getMsg(), null);
    }

    public static HttpResult OK(Object object) {
        return OK(HttpResultCode.OK.getMsg(), object);
    }

    public static HttpResult OK(String msg) {
        return OK(msg, null);
    }

    public static HttpResult OK(String msg, Object object) {
        return CustomResult(HttpResultCode.OK.getCode(), msg, object);
    }

    public static HttpResult Fail() {
        return Fail(HttpResultCode.FAIL.getMsg());
    }

    public static HttpResult Fail(String msg) {
        return CustomResult(HttpResultCode.FAIL.getCode(), msg, null);
    }

    public static HttpResult CustomResult(HttpResultCode resultCode) {
        return CustomResult(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static HttpResult CustomResult(Integer code, String msg, Object data) {
        return HttpResult.builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }
}
