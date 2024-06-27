package pers.johns.crm.constant;

/**
 * ClassName    : Constants
 * <br/>
 * Description  : 常量信息类
 * <br/>
 * CreateTime   : 2024/6/27 16:26
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

public class Constants {
    /**
     * 登录的默认请求路径
     */
    public static final String LOGIN_URI = "/api/login";

    /**
     * 在 Redis 中存储 JWT 的前缀
     */
    public static final String REDIS_JWT_KEY_PREFIX = "crm:user:login:";

    /**
     * 当用户勾选记住登录时，Token 的过期时间，以毫秒为单位，30 分钟
     */
    public static final long DEFAULT_REMEMBER_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 当用户不勾选记住登录时，Token 的过期时间，以毫秒为单位，7 天
     */
    public static final long DEFAULT_NOT_REMEMBER_TIME = 1000 * 60 * 30;
}
