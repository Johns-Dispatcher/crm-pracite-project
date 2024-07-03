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
    FAIL(500, "请求失败"),
    TOKEN_IS_EMPTY(901, "请求未提供 Token"),
    TOKEN_IS_EXPIRED(902, "JWT 已经过期，请重新登录"),
    TOKEN_VERIFY_FAILED(903, "Token 验证失败，可能是 Token 非法或者已经超时"),
    DATA_ACCESS_EXCEPTION(500, "数据库操作失败"),
    USER_SERVICE_EXCEPTION(500, "用户业务操作失败"),
    ACTIVITY_SERVICE_EXCEPTION(500, "活动业务操作失败"),
    ACTIVITY_REMARK_SERVICE_EXCEPTION(500, "活动备注业务操作失败")
    ;

    private final Integer code;
    private final String msg;
}
