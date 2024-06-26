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
     *
     */
    private Object data;

    public static HttpResult OK() {
        return OK(HttpResultCode.OK.getMsg());
    }

    public static HttpResult OK(String msg) {
        return HttpResult.builder()
                .code(HttpResultCode.OK.getCode())
                .msg(msg)
                .build();
    }

    public static HttpResult Fail() {
        return Fail(HttpResultCode.FAIL.getMsg());
    }

    public static HttpResult Fail(String msg) {
        return HttpResult.builder()
                .code(HttpResultCode.FAIL.getCode())
                .msg(msg)
                .build();
    }
}
