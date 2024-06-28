package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.User;

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
            """)
    @ResultMap("UserBaseMap")
    List<User> selectAll();

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

    @Update("""
            update t_user
            set
                login_act = #{loginAct}, login_pwd = #{loginPwd},
                name = #{name}, phone = #{phone}, email = #{email},
                account_no_expired = #{accountNoExpired},
                credentials_no_expired = #{credentialsNoExpired},
                account_no_locked = #{accountNoLocked},
                account_enabled = #{accountEnbaled},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy},
                last_login_time = #{lastLoginTime}
            where id = #{id}
            """)
    Integer updateUser(User user);

    @Delete("""
            delete from t_user where id = #{id}
            """)
    Integer deleteById(Integer id);
}
