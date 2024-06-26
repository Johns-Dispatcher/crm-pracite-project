package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.Permission;

import java.util.List;

/**
 * InterfaceName : PermissionMapper
 * <br/>
 * Description   : 用于操作 t_permission 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 20:14
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface PermissionMapper {

    @Select("""
            select
                id, name, code, url, type,
                parent_id, order_no, icon
            from t_permission
            where id = #{id}
            """)
    @Results(id = "PermissionBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "code", property = "code"),
            @Result(column = "url", property = "url"),
            @Result(column = "type", property = "type"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "order_no", property = "orderNo"),
            @Result(column = "icon", property = "icon"),
    })
    Permission selectById(Integer id);

    @Select("""
            select
                id, name, code, url, type,
                parent_id, order_no, icon
            from t_permission
            """)
    @ResultMap("PermissionBaseMap")
    List<Permission> selectAll();

    @Select("""
            select distinct
                tp.id, tp.name, tp.code, tp.url, tp.type,
                tp.parent_id, tp.order_no, tp.icon
            from t_permission tp
            inner join t_role_permission trp
            on tp.id = trp.permission_id
            where trp.role_id = #{roleId}
            """)
    @ResultMap("PermissionBaseMap")
    List<Permission> selectPermissionsByRoleId(Integer roleId);

    @Insert("""
            insert into t_permission (
                id, name, code, url, type,
                parent_id, order_no, icon)
            values (
                null, #{name}, #{code}, #{url}, #{type},
                #{parentId}, #{orderNo}, #{icon}
            )
            """)
    Integer insertPermission(Permission permission);

    @Update("""
            update t_permission set
                name = #{name}, code = #{code}, url = #{url},
                type = #{type}, parent_id = #{parentId},
                order_no = #{orderNo}, icon = #{icon}
            where id = #{id}
            """)
    Integer updatePermission(Permission permission);
}
