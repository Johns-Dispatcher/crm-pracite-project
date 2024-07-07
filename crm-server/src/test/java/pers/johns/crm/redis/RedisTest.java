package pers.johns.crm.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import pers.johns.crm.constant.Constants;

import java.util.List;

/**
 * ClassName    : RedisTest
 * <br/>
 * Description  : 测试 Redis 相关功能
 * <br/>
 * CreateTime   : 2024/7/7 20:13
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testBatch() {
        ScanOptions options = ScanOptions.scanOptions()
                .count(1000)
                .match(Constants.ACTIVITY_PREFIX_REDIS_KEY + "*")
                .build();

        List<String> keys = redisTemplate.scan(options).stream().toList();
        log.info(keys.toString());
    }
}
