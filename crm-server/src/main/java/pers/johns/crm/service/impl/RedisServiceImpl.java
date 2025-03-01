package pers.johns.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pers.johns.crm.service.RedisService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName    : RedisServiceImpl
 * <br/>
 * Description  : Redis 实现类
 * <br/>
 * CreateTime   : 2024/6/27 16:23
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final StringRedisTemplate redisTemplate;

    private static final Integer DEFAULT_BATCH_COUNT = 1000;

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValue(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean removeValue(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void setExpireTime(String key, long expireTime) {
        redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }
    @Override
    public Long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    @Override
    public List<String> getKeys(String keyPrefix) {
        return redisTemplate.scan(
                ScanOptions
                        .scanOptions()
                        .count(DEFAULT_BATCH_COUNT)
                        .match(keyPrefix + "*")
                        .build()
        ).stream().toList();
    }
}
