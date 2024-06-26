package pers.johns.crm.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * EnumName     : HttpResultCode
 * <br/>
 * EnumValue    :
 * <br/>
 * Description  : 用于 HTTP 请求结果的默认值
 * <br/>
 * CreateTime   : 2024/6/26 10:04
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Getter
@AllArgsConstructor
public enum HttpResultCode {
    OK(200, "请求成功"),
    FAIL(500, "请求失败");

    private final Integer code;
    private final String msg;
}
