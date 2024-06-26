package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.SystemInfo;

import java.util.List;

/**
 * InterfaceName : SystemInfoMapper
 * <br/>
 * Description   : 用于操作 t_system_info 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 15:32
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

public interface SystemInfoMapper {

    @Select("""
            select
                id, system_code, name,
                site, logo, title, description,
                keywords, shortcut_icon, tel, wechat,
                email, address, version, close_msg, is_open,
                create_time, create_by,
                edit_time, edit_by
            from t_system_info
            where id = #{id}
            """)
    @Results(id = "SystemInfoBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "system_code", property = "systemCode"),
            @Result(column = "name", property = "name"),
            @Result(column = "site", property = "site"),
            @Result(column = "logo", property = "logo"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "keywords", property = "keywords"),
            @Result(column = "shortcut_icon", property = "shortcutIcon"),
            @Result(column = "tel", property = "tel"),
            @Result(column = "wechat", property = "wechat"),
            @Result(column = "email", property = "email"),
            @Result(column = "address", property = "address"),
            @Result(column = "version", property = "version"),
            @Result(column = "close_msg", property = "closeMsg"),
            @Result(column = "is_open", property = "isOpen"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy")
    })
    SystemInfo selectById(Integer id);

    @Select("""
            select
                id, system_code, name,
                site, logo, title, description,
                keywords, shortcut_icon, tel, wechat,
                email, address, version, close_msg, is_open,
                create_time, create_by,
                edit_time, edit_by
            from t_system_info
            """)
    @ResultMap("SystemInfoBaseMap")
    List<SystemInfo> selectAll();

    @Insert("""
            insert into t_system_info(
                id, system_code, name,
                site, logo, title, description,
                keywords, shortcut_icon, tel, wechat,
                email, address, version, close_msg, is_open,
                create_time, create_by,
                edit_time, edit_by)
            values (
                null, #{systemCode}, #{name},
                #{site}, #{logo}, #{title}, #{description},
                #{keywords}, #{shortcutIcon}, #{tel}, #{wechat},
                #{email}, #{address}, #{version}, #{closeMsg}, #{isOpen},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertSystemInfo(SystemInfo systemInfo);

    @Update("""
            update t_system_info set
                system_code = #{systemCode}, name = #{name},
                site = #{site}, logo = #{logo}, title = #{title},
                description = #{description},
                keywords = #{keywords}, shortcut_icon = #{shortcutIcon},
                tel = #{tel}, wechat = #{wechat},
                email = #{email}, address = #{address},
                version = #{version}, close_msg = #{closeMsg}, is_open = #{isOpen},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateSystemInfo(SystemInfo systemInfo);
}
