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

    private final static Long DEFAULT_EXPIRE_TIME = 1000 * 60 * 30L;

    public <T> T getValue(String key, Class<T> clazz) {
        String jsonData = redisService.getValue(key);
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        } else {
            return JsonUtils.toBean(jsonData, clazz);
        }
    }

    public <T> List<T> getList(String key) {
        String jsonData = redisService.getValue(key);
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        } else {
            return JsonUtils.toList(jsonData);
        }
    }

    public void saveValue(String key, Object object) {
        saveValue(key, object, DEFAULT_EXPIRE_TIME);
    }

    public void saveValue(String key, Object object, Long expireTime) {
        String jsonData = JsonUtils.toJson(object);
        redisService.setValue(key, jsonData, expireTime);
    }

    public void deleteValue(String key) {
        redisService.removeValue(key);
    }
}
