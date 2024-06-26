package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.TranRemark;

import java.util.List;

/**
 * InterfaceName : TranRemarkMapper
 * <br/>
 * Description   : 用于操作 t_tran_remark 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 22:17
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface TranRemarkMapper {
    @Select("""
            select
                id, tran_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_tran_remark
            where id = #{id}
            """)
    @Results(id = "TranRemarkBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "note_way", property = "noteWay"),
            @Result(column = "note_content", property = "noteContent"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "deleted", property = "deleted")
    })
    TranRemark selectById(Integer id);

    @Select("""
            select
                id, tran_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            from t_tran_remark
            """)
    @ResultMap("TranRemarkBaseMap")
    List<TranRemark> selectAll();

    @Insert("""
            insert into t_tran_remark (
                id, tran_id,
                note_way, note_content,
                create_time, create_by,
                edit_time, edit_by,
                deleted
            )  values (
                null, #{tranId},
                #{noteWay}, #{noteContent},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy},
                #{deleted}
            )
            """)
    Integer insertTranRemark(TranRemark tranRemark);

    @Update("""
            update t_tran_remark set
                tran_id = #{tranId},
                note_way = #{noteWay}, note_content = #{noteContent},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy},
                deleted = #{deleted}
            where id = #{id}
            """)
    Integer updateTranRemark(TranRemark tranRemark);

    @Delete("""
            delete from t_tran_remark where id = #{id}
            """)
    Integer deleteTranRemarkById(Integer id);
}
