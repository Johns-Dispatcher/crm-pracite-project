package pers.johns.crm.service;

/**
 * InterfaceName : RedisService
 * <br/>
 * Description   : Redis 相关接口
 * <br/>
 * CreateTime    : 2024/6/27 16:22
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

public interface RedisService {
    /**
     * 为指定键指定值并设置过期时间
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间，单位毫秒
     */
    void setValue(String key, String value, long expireTime);

    /**
     * 为指定键指定值
     * @param key 键
     * @param value 值
     */
    void setValue(String key, String value);

    /**
     * 获取对应键的值
     * @param key 键
     * @return 对应值
     */
    Object getValue(String key);

    /**
     * 移除指定键
     * @param key 键
     * @return 是否移除成功
     */
    Boolean removeValue(String key);

    /**
     * 为指定键设置过期时间
     * @param key 键
     * @param expireTime 过期时间，单位毫秒
     */
    void setExpireTime(String key, long expireTime);

    /**
     * 获取剩余时间
     * @param key 键
     * @return 剩余过期时间
     */
    Long getExpireTime(String key);
}
