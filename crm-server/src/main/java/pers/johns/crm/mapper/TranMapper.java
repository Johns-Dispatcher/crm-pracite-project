package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.mode.po.Tran;

import java.util.List;

/**
 * InterfaceName : TranMapper
 * <br/>
 * Description   : 用于操作 t_tran 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 21:41
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface TranMapper {

    @Select("""
            select
                id, tran_no,
                customer_id, money,
                expected_date, stage,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_tran
            where id = #{id}
            """)
    @Results(id = "TranBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "tran_no", property = "tranNo"),
            @Result(column = "customer_id", property = "customerId"),
            @Result(column = "money", property = "money"),
            @Result(column = "expected_date", property = "expectedDate"),
            @Result(column = "stage", property = "stage"),
            @Result(column = "description", property = "description"),
            @Result(column = "next_contact_time", property = "nextContactTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
    })
    Tran selectById(Integer id);

    @Select("""
            select
                id, tran_no,
                customer_id, money,
                expected_date, stage,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_tran
            """)
    @ResultMap("TranBaseMap")
    List<Tran> selectAll();

    @Insert("""
            insert into t_tran (
                id, tran_no,
                customer_id, money,
                expected_date, stage,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            ) values (
                null, #{tranNo},
                #{customerId}, #{money},
                #{expectedDate}, #{state},
                #{description}, #{nextContactTime},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertTran(Tran tran);

    @Update("""
            update t_tran
            set
                tran_no = #{tranNo},
                customer_id = #{customerId}, money = #{money},
                expected_date = #{expectedDate}, stage = #{stage},
                description = #{description}, next_contact_time = #{nextContactTime},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateTran(Tran tran);

    @Delete("""
            delete from t_tran where id = #{id}
            """)
    Integer deleteTranById(Integer id);
}
