package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import pers.johns.crm.annotation.DataScope;
import pers.johns.crm.model.po.User;
import pers.johns.crm.query.DataFilterQuery;

import java.util.List;

/**
 * InterfaceName : UserMapper
 * <br/>
 * Description   : 用于操作 t_user 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 14:39
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface UserMapper {

    @Select("""
            select
                id,
                login_act, login_pwd,
                name, phone, email,
                account_no_expired, credentials_no_expired,
                account_no_locked, account_enabled,
                create_time, create_by,
                edit_time, edit_by,
                last_login_time
            from t_user
            where id = #{id}
            """)
    @Results(id = "UserBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "login_act", property = "loginAct"),
            @Result(column = "login_pwd", property = "loginPwd"),
            @Result(column = "name", property = "name"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "email", property = "email"),
            @Result(column = "account_no_expired", property = "accountNoExpired"),
            @Result(column = "credentials_no_expired", property = "credentialsNoExpired"),
            @Result(column = "account_no_locked", property = "accountNoLocked"),
            @Result(column = "account_enabled", property = "accountEnabled"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "last_login_time", property = "lastLoginTime"),
            @Result(column = "id", property = "roles", one = @One(
                    select = "pers.johns.crm.mapper.RoleMapper.selectRolesByUserId"
            ))
    })
    User selectById(Integer id);

    @DataScope(tableField = "id")
    List<User> selectAll(DataFilterQuery dataFilterQuery);

    @Select("""
            select name from t_user where id = #{id}
            """)
    String selectNameById(Integer id);

    @Select("""
            select
                id,
                login_act, login_pwd,
                name, phone, email,
                account_no_expired, credentials_no_expired,
                account_no_locked, account_enabled,
                create_time, create_by,
                edit_time, edit_by,
                last_login_time
            from t_user
            where login_act = #{loginAct}
            """)
    @ResultMap("UserBaseMap")
    User selectByLoginAct(String LoginAct);

    @Select("""
            select count(*) from t_user
            """)
    Integer countAll();

    @Select("""
            select count(*) from t_user
            where login_act = #{loginAct}
            """)
    Integer checkLoginAct(String loginAct);

    @Insert("""
            insert into t_user(
                id,
                login_act, login_pwd,
                name, phone, email,
                account_no_expired, credentials_no_expired,
                account_no_locked, account_enabled,
                create_time, create_by,
                edit_time, edit_by,
                last_login_time)
            values (
                null,
                #{loginAct}, #{loginPwd},
                #{name}, #{phone}, #{email},
                #{accountNoExpired}, #{credentialsNoExpired},
                #{accountNoLocked}, #{accountEnabled},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy},
                #{lastLoginTime}
            )
            """)
    Integer insertUser(User user);

    Integer updateUser(User user);

    @Delete("""
            delete from t_user where id = #{id}
            """)
    Integer deleteById(Integer id);

    Integer deleteUses(List<Integer> ids);
}
