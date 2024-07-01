package pers.johns.crm.annotation;

import java.lang.annotation.*;

/**
 * AnnotationName : DataScope
 * <br/>
 * Description    : 数据范围注解，在 SQL 语句末尾添加过滤条件 <br/>
 * 会添加 where tableAlias.tableField = loginId
 * <br/>
 * CreateTime     : 2024/7/1 13:25
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 指定对什么字段进行过滤
     * @return 表字段名
     */
    public String tableField() default "";
}
