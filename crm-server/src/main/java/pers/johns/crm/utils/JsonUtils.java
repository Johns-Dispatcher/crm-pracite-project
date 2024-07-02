package pers.johns.crm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

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
    private JsonUtils() {}

    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
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

    /**
     * 将指定 json 数据转换为对应的 List 集合，使用 TypeReference 保证转换检测
     * @param json json 数据字符串
     * @return {@link List} 集合
     * @param <T> 集合中元素类型
     */
    public static <T> List<T> toList(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<T>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
