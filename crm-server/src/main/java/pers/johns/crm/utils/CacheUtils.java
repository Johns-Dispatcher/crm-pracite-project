package pers.johns.crm.utils;

import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ClassName    : CacheUtils
 * <br/>
 * Description  : 缓存工具类
 * <br/>
 * CreateTime   : 2024/7/2 13:16
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class CacheUtils {

    /**
     * 从缓存中获取数据，如果获取不到就查询数据库并写入缓存
     * @param cacheSelector 缓存生产者 {@link Supplier} 用于获取缓存数据
     * @param databaseSelector 数据库生产者 {@link Supplier} 用于从数据库中获取数据
     * @param cacheAccepter 缓存消费者 {@link Consumer} 接收数据库的数据，并向缓存中写入
     * @return 查询的值
     * @param <T> 查询的类型
     */
    public static <T> T getCacheData(
            // 从缓存获取
            Supplier<T> cacheSelector,
            // 从数据库中获取
            Supplier<T> databaseSelector,
            // 向缓存存储
            Consumer<T> cacheAccepter
    ) {
        // 首先从缓存中获取数据
        T data = cacheSelector.get();
        // 如果没有数据就从数据库中获取
        if (ObjectUtils.isEmpty(data)) {
            data = databaseSelector.get();
            // 如果数据库中能够查到结果就写入缓存
            if (!Objects.isNull(data)) {
                cacheAccepter.accept(data);
            }
        }
        return data;
    }

    /**
     * 按照指定键值删除缓存数据
     * @param cache 缓存消费者 {@link Consumer} 接收键值，并删除该键值的值
     * @param key 键值
     */
    public static void removeCacheData(
            Consumer<String> cache,
            String key
    ) {
        cache.accept(key);
    }
}
