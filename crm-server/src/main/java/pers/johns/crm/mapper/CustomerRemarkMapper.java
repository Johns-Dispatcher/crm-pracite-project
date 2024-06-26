package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.CustomerRemark;

import java.util.List;

/**
 * InterfaceName : CustomerRemarkMapper
 * <br/>
 * Description   : 用于操作 t_customer_remark 的 dao
 * <br/>
 * CreateTime    : 2024/6/25 13:43
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface CustomerRemarkMapper {
    @Select("""
            select
                id, customer_id,
                note_way, note_content,
                create_by, create_time,
                edit_time, edit_by,
                deleted
            from t_customer_remark
            where id = #{id}
            """)
    @Results(id = "CustomerRemarkBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "customer_id", property = "customerId"),
            @Result(column = "note_way", property = "noteWay"),
            @Result(column = "note_content", property = "noteContent"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "deleted", property = "deleted")
    })
    CustomerRemark selectById(Integer id);

    @Select("""
            select
                id, customer_id,
                note_way, note_content,
                create_by, create_time,
                edit_time, edit_by,
                deleted
            from t_customer_remark
            """)
    @ResultMap("CustomerRemarkBaseMap")
    List<CustomerRemark> selectAll();

    @Select("""
            select
                id, customer_id,
                note_way, note_content,
                create_by, create_time,
                edit_time, edit_by,
                deleted
            from t_customer_remark
            where customer_id = #{customerId}
            """)
    @ResultMap("CustomerRemarkBaseMap")
    List<CustomerRemark> selectCustomerRemarksByCustomerId(Integer customerId);

    @Insert("""
            insert into t_customer_remark (
                id, customer_id,
                note_way, note_content,
                create_by, create_time,
                edit_time, edit_by,
                deleted
            ) values (
                null, #{customerId},
                #{noteWay}, #{noteContent},
                #{createBy}, #{createTime},
                #{editTime}, #{editBy},
                #{deleted}
            )
            """)
    Integer insertCustomerRemark(CustomerRemark customerRemark);

    @Update("""
            update t_customer_remark
            set
                customer_id = #{customerId},
                note_way = #{noteWay}, note_content = #{noteContent},
                create_by = #{createBy}, create_time = #{createTime},
                edit_by = #{editBy}, edit_time = #{editTime},
                deleted = #{deleted}
            where id = #{id}
            """)
    Integer updateCustomerRemark(CustomerRemark customerRemark);

    @Delete("""
            delete from t_customer_remark where id = #{id}
            """)
    Integer deleteCustomerRemarkById(Integer id);
}
