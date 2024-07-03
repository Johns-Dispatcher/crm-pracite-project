package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.annotation.DataScope;
import pers.johns.crm.model.po.Activity;
import pers.johns.crm.query.ActivitySearchQuery;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName : ActivityMapper
 * <br/>
 * Description   : 用于操作 t_activity 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 21:43
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ActivityMapper {

    @Select("""
            select
                id, owner_id, name,
                start_time, end_time,
                cost, description,
                create_time, create_by,
                edit_time, edit_by
            from t_activity
            where id = #{id}
            """)
    @Results(id = "ActivityBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "owner_id", property = "ownerId"),
            @Result(column = "name", property = "name"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "cost", property = "cost"),
            @Result(column = "description", property = "description"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "id", property = "activityRemarks", one = @One(
                    select = "pers.johns.crm.mapper.ActivityRemarkMapper.selectByActivityId"
            ))
    })
    Activity selectById(Integer id);

    @DataScope(tableField = "owner_id")
    List<Activity> selectAll(ActivitySearchQuery activitySearchQuery);

    @Insert("""
            insert into t_activity (
                id, owner_id, name,
                start_time, end_time,
                cost, description,
                create_time, create_by,
                edit_time, edit_by
            ) values (
                null, owner_id = #{ownerId}, name = #{name},
                start_time = #{startTime}, end_time = #{endTime},
                cost = #{cost}, description = #{description},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            )
            """)
    Integer insertActivity(Activity activity);

    @Update("""
            update t_activity
            set
                owner_id = #{ownerId}, name = #{name},
                start_time = #{startTime}, end_time = #{endTime},
                cost = #{cost}, description = #{description},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateActivity(Activity activity);

    @Delete("""
            delete from t_activity where id = #{id}
            """)
    Integer deleteActivityById(Integer id);


    @Select("""
            SELECT DISTINCT
            	ta.owner_id as ownerId,
            	tu.name
            FROM
            	t_activity ta
            INNER JOIN t_user tu
            ON ta.owner_id = tu.id
            """)
    List<Map<String, Object>> selectOwnerHavingActivity();
}
