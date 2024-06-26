package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.TranHistory;

import java.util.List;

/**
 * InterfaceName : TranHistoryMapper
 * <br/>
 * Description   : 用于操作 t_tran_history 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 22:53
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface TranHistoryMapper {

    @Select("""
            select
                id, tran_id,
                stage, money,
                expected_date,
                create_time, create_by
            from t_tran_history
            where id = #{id}
            """)
    @Results(id = "TranHistoryBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "tran_id", property = "tranId"),
            @Result(column = "stage", property = "stage"),
            @Result(column = "money", property = "money"),
            @Result(column = "expected_date", property = "expectedDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy")
    })
    TranHistory selectById(Integer id);

    @Select("""
            select
                id, tran_id,
                stage, money,
                expected_date,
                create_time, create_by
            from t_tran_history
            """)
    @ResultMap("TranHistoryBaseMap")
    List<TranHistory> selectAll();

    @Insert("""
            insert into t_tran_history (
                id, tran_id,
                stage, money,
                expected_date,
                create_time, create_by
            ) values (
                null, #{tranId},
                #{stage}, #{money},
                #{expectedData},
                #{createTime}, #{createBy}
            )
            """)
    Integer insertTranHistory(TranHistory tranHistory);

    @Update("""
            update t_tran_history set
                tran_id = #{tranId},
                stage = #{stage}, money = #{money},
                expected_date = #{expectedDate},
                create_time = #{createTime}, create_by = #{createBy}
            where id = #{id}
            """)
    Integer updateTranHistory(TranHistory tranHistory);

    @Delete("""
            delete from t_tran_history where id = #{id}
            """)
    Integer deleteTranHistoryById(Integer id);
}
