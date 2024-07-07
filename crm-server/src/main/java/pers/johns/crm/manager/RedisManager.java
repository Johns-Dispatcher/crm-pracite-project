package pers.johns.crm.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.johns.crm.service.RedisService;
import pers.johns.crm.utils.JsonUtils;

import java.util.List;

/**
 * ClassName    : RedisManager
 * <br/>
 * Description  : 用于 Redis 的共用业务代码<br/>
 * 采用 JSON 串在 Redis 中存储数据
 * <br/>
 * CreateTime   : 2024/7/2 14:35
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisManager {

    private final RedisService redisService;

    /**
     * 默认的过期时间
     */
    private final static Long DEFAULT_EXPIRE_TIME = 1000 * 60 * 30L;

    /**
     * 从 Redis 中获取对应值，并且反序列化
     * @param key Redis 对应字段值
     * @param clazz 数据类型
     * @return 反序列化后的 Java 对象
     * @param <T> 数据类型
     */
    public <T> T getValue(String key, Class<T> clazz) {
        String jsonData = redisService.getValue(key);
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        } else {
            return JsonUtils.toBean(jsonData, clazz);
        }
    }

    /**
     * 从 Redis 中读取列表类型的数据，使用默认的 jackson 进行反序列化
     * <br/>
     * 有时反序列化会出现问题，不建议使用，请使用 {@link RedisManager#getList(String, Class)}
     * @param key 键值
     * @return 列表对象
     * @param <T> 数据类型
     */
    @Deprecated
    public <T> List<T> getList(String key) {
        String jsonData = redisService.getValue(key);
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        } else {
            return JsonUtils.toList(jsonData);
        }
    }

    /**
     * 从 Redis 中读取列表类型的数据，使用 fastjson 进行反序列化
     * @param key 键值
     * @param clazz 数据类型
     * @return 列表对象
     * @param <T> 数据类型
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        String jsonData = redisService.getValue(key);
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        } else {
            return JsonUtils.toList(jsonData, clazz);
        }
    }

    /**
     * 向 Redis 中存储数据
     * @see RedisManager#saveValue(String, Object, Long)
     * @param key 键值
     * @param object 数据
     */
    public void saveValue(String key, Object object) {
        saveValue(key, object, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 向 Redis 中存储数据，将数据序列化之后进行存储
     * @param key 键值
     * @param object 数据
     * @param expireTime 过期时间
     */
    public void saveValue(String key, Object object, Long expireTime) {
        String jsonData = JsonUtils.toJson(object);
        redisService.setValue(key, jsonData, expireTime);
    }

    /**
     * 删除对应键值的数据
     * @param key 键值
     */
    public void deleteValue(String key) {
        redisService.removeValue(key);
    }

    /**
     * 批量删除指定前缀的数据
     * @param keyPrefix 键值前缀
     */
    public void batchDeleteValues(String keyPrefix) {
        redisService.getKeys(keyPrefix).forEach(redisService::removeValue);
    }
}
