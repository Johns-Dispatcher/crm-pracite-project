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

    /* == 请求路径相关 == */

    /**
     * 登录的默认请求路径
     */
    public static final String LOGIN_URI = "/api/login";

    /**
     * 退出的默认请求路径
     */
    public static final String LOGOUT_URI = "/api/logout";

    /* == 请求参数相关 == */

    /**
     * 登录时的用户名参数名称
     */
    public static final String LOGIN_USERNAME_PARAMETER = "loginAct";

    /**
     * 登录时的密码参数名称
     */
    public static final String LOGIN_PASSWORD_PARAMETER = "loginPwd";

    /* == JWT 缓存相关 == */

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

    /* == JWT 认证请求相关 == */

    /**
     * 认证信息头的参数名
     */
    public static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";

    /**
     * Token 的前缀部分
     */
    public static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    /* == 分页查询径相关 == */

    /**
     * 默认的分页参数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认的备注分页参数
     */
    public static final Integer DEFAULT_REMARK_PAGE_SIZE = 2;


    /* == 角色相关 == */

    /**
     * 默认管理员角色名称
     */
    public static final String DEFAULT_ADMIN_ROLE_NAME = "admin";

    /* == Redis 缓存键值相关 == */

    /**
     * 活动拥有者缓存存储字段
     */
    public static final String ACTIVITY_OWNER_REDIS_KEY = "crm:activity:owners";

    /**
     * 用户名缓存存储字段
     */
    public static final String USER_NAME_REDIS_KEY = "crm:user:names";

    /**
     * 活动缓存字段的前缀
     */
    public static final String ACTIVITY_PREFIX_REDIS_KEY = "crm:activity:";

    /**
     * 活动名称缓存字段
     */
    public static final String ACTIVITY_NAME_REDIS_KEY = "crm:activity:names";

    /**
     * 目前正在进行的活动名称的缓存字段
     */
    public static final String ACTIVITY_ONGOING_NAME_REDIS_KEY = "crm:activity:ongoing";

    /**
     * 字典缓存前缀
     */
    public static final String DIC_VALUE_PREFIX_REDIS_KEY = "crm:dic:";

    /**
     * 产品名称缓存字段
     */
    public static final String PRODUCT_NAME_REDIS_KEY = "crm:product:names";

}
