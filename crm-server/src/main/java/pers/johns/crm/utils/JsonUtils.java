package pers.johns.crm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * ClassName    : JsonUtils
 * <br/>
 * Description  : JSON 相关工具类
 * <br/>
 * CreateTime   : 2024/6/26 11:00
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class JsonUtils {
    private JsonUtils() {};

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            // 添加日期模块支持
            .addModule(new JavaTimeModule())
            .build();

    /**
     * 将 Java 对象转换为 json 字符串
     * @param object Java 对象
     * @return json 字符串
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 json 字符串转换回 Java 对象
     * @param json json 字符串
     * @param clazz Java 类，指定对象的类型
     * @return Java 对象
     * @param <T> 对象类型
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
