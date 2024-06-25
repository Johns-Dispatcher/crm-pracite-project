package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.mode.po.Customer;

import java.util.List;

/**
 * InterfaceName : CustomerMapper
 * <br/>
 * Description   : 用于操作 t_customer 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 12:46
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface CustomerMapper {

    @Select("""
            select
                id, clue_id,
                product, description,
                next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_customer
            where id = #{id}
            """)
    @Results(id = "CustomerBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "clue_id", property = "clueId"),
            @Result(column = "product", property = "product"),
            @Result(column = "description", property = "description"),
            @Result(column = "next_contact_time", property = "nextContactTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "id", property = "customerRemarks", one = @One(
                    select = "pers.johns.crm.mapper.CustomerRemarkMapper.selectCustomerRemarksByCustomerId"
            ))
    })
    Customer selectById(Integer id);

    @Select("""
            select
                id, clue_id,
                product, description,
                next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_customer
            """)
    @ResultMap("CustomerBaseMap")
    List<Customer> selectAll();

    @Insert("""
            insert into t_customer (
                id, clue_id,
                product, description,
                next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            ) values (
                null, #{clueId},
                #{product}, #{description},
                #{nextContactTime},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertCustomer(Customer customer);

    @Update("""
            update t_customer
            set
                clue_id = #{clud_id},
                product = #{product}, description = #{description},
                next_contact_time = #{nextContactTime},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateCustomer(Customer customer);

    @Delete("""
            delete from t_customer where id = #{id}
            """)
    Integer deleteCustomerById(Integer id);
}
