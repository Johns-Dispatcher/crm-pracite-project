package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.Role;

import java.util.List;

/**
 * InterfaceName : RoleMapper
 * <br/>
 * Description   : 用于操作 t_role 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 19:57
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface RoleMapper {

    @Select("""
            select id, role, role_name
            from t_role
            where id =#{id}
            """)
    @Results(id = "RoleBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "role", property = "role"),
            @Result(column = "role_name", property = "roleName"),
            @Result(column = "id", property = "permissions", one = @One(
                    select = "pers.johns.crm.mapper.PermissionMapper.selectPermissionsByRoleId"
            ))
    })
    Role selectById(Integer id);

    @Select("""
            select id, role, role_name
            from t_role
            """)
    @ResultMap("RoleBaseMap")
    List<Role> selectAll();

    @Select("""
            select tr.id, tr.role, tr.role_name
            from t_role tr
            inner join t_user_role tur
            on tr.id = tur.role_id
            where tur.user_id = #{userId}
            """)
    @ResultMap("RoleBaseMap")
    List<Role> selectRolesByUserId(Integer userId);

    @Insert("""
            insert into t_role(
                id, role, role_name
            ) values (
                #{id}, #{role}, #{roleName}
            )
            """)
    Integer insertRole(Role role);

    @Update("""
            update t_role set
                role = #{role}, role_name = #{roleName}
            where id = #{id}
            """)
    Integer updateRole(Role role);

    @Delete("""
            delete from t_role where id = #{id}
            """)
    Integer deleteById(Integer id);
}
