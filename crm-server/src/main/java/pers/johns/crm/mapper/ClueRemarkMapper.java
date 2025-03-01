package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.annotation.DataScope;
import pers.johns.crm.model.po.ClueRemark;
import pers.johns.crm.query.ClueRemarkQuery;

import java.util.List;

/**
 * InterfaceName : ClueRemarkMapper
 * <br/>
 * Description   : 用于操作 t_clue_remark 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 11:02
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ClueRemarkMapper {

    @Select("""
            select
                id, clue_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_clue_remark
            where id = #{id}
            """)
    @Results(id = "ClueRemarkBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "clue_id", property = "clueId"),
            @Result(column = "note_way", property = "noteWay"),
            @Result(column = "note_content", property = "noteContent"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "deleted", property = "deleted"),
    })
    ClueRemark selectById(Integer id);

    List<ClueRemark> selectAll(ClueRemarkQuery clueRemarkQuery);

    List<ClueRemark> selectAllByClueId(ClueRemarkQuery clueRemarkQuery);

    @Select("""
            select
                id, clue_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_clue_remark
            where clue_id = #{clueId}
            """)
    @ResultMap("ClueRemarkBaseMap")
    List<ClueRemark> selectClueRemarksByClueId(Integer clueId);

    @Select("""
            select count(1) from t_clue_remark where clue_id = #{clueId}
            """)
    Integer countByClueId(Integer clueId);

    Integer countByClueIds(List<Integer> clueIds);

    @Insert("""
            insert into t_clue_remark (
                id, clue_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            ) values (
                null, #{clueId},
                #{noteWay}, #{noteContent},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy},
                #{deleted}
            )
            """)
    Integer insertClueRemark(ClueRemark clueRemark);

    Integer updateClueRemark(ClueRemark clueRemark);

    @Delete("""
            delete from t_clue_remark where id = #{id}
            """)
    Integer deleteClueRemarkById(Integer id);

    @Delete("""
            delete from t_clue_remark where clue_id = #{clueId}
            """)
    Integer deleteClueRemarkByClueId(Integer clueId);

    Integer deleteBulkClueRemarksByClueId(List<Integer> clueIds);
}
