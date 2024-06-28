package pers.johns.crm.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pers.johns.crm.utils.JsonUtils;

/**
 * ClassName    : RedisConfig
 * <br/>
 * Description  : Redis 相关配置
 * <br/>
 * CreateTime   : 2024/6/28 12:23
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Configuration
@RequiredArgsConstructor
public class RedisConfig implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.EVERYTHING
        );

        // 设置 Key 序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置 Value 序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));
        // 设置 Hash Key 序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置 Hash Value 序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));
    }
}
