package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.mode.po.ActivityRemark;

import java.util.List;

/**
 * InterfaceName : ActivityRemarkMapper
 * <br/>
 * Description   : 用于操作 t_activity_remark 的 Dao
 * <br/>
 * CreateTime    : 2024/6/24 23:15
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ActivityRemarkMapper {

    @Select("""
            select
                id, activity_id, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_activity_remark
            where id = #{id}
            """)
    @Results(id = "ActivityRemarkBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "activity_id", property = "activityId"),
            @Result(column = "note_content", property = "noteContent"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "deleted", property = "deleted"),
    })
    ActivityRemark selectById(Integer id);

    @Select("""
            select
                id, activity_id, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_activity_remark
            """)
    @ResultMap("ActivityRemarkBaseMap")
    List<ActivityRemark> selectAll();

    @Select("""
            select
                id, activity_id, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_activity_remark
            where activity_id = #{activityId}
            """)
    @ResultMap("ActivityRemarkBaseMap")
    List<ActivityRemark> selectByActivityId(Integer activityId);

    @Insert("""
            insert into t_activity_remark (
                id, activity_id, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            ) values (
                null, #{activityId}, #{noteContent},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy},
                #{deleted}
            )
            """)
    Integer insertActivityRemark(ActivityRemark activityRemark);

    @Update("""
            update t_activity_remark
            set
                activity_id = #{activityId}, note_content = #{noteContent},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy},
                deleted = #{deleted}
            where id = #{id}
            """)
    Integer updateActivityRemark(ActivityRemark activityRemark);

    @Delete("""
            delete from t_activity_remark where id = #{id}
            """)
    Integer deleteActivityRemarkById(Integer id);
}
