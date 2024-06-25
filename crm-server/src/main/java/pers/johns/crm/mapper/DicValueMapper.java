package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.mode.po.DicValue;

import java.util.List;

/**
 * InterfaceName : DicValueMapper
 * <br/>
 * Description   : 用于操作 t_dic_value 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 23:19
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface DicValueMapper {

    @Select("""
            select id, type_code, type_value, `order`, remark
            from t_dic_value
            where id = #{id}
            """)
    @Results(id = "DicValueBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "type_code", property = "typeCode"),
            @Result(column = "type_value", property = "typeValue"),
            @Result(column = "order", property = "order"),
            @Result(column = "remark", property = "remark")
    })
    DicValue selectById(Integer id);

    @Select("""
            select id, type_code, type_value, `order`, remark
            from t_dic_value
            """)
    @ResultMap("DicValueBaseMap")
    List<DicValue> selectAll();

    @Insert("""
            insert into t_dic_value (
                id, type_code, type_value, `order`, remark
            ) values (
                null, #{typeCode}, #{typeValue}, #{order}, #{remark}
            )
            """)
    Integer insertDicValue(DicValue dicValue);

    @Update("""
            update t_dic_value set
                type_code = #{typeCode},
                type_value = #{typeValue},
                `order` = #{order},
                remark = #{remark}
            where id = #{id}
            """)
    Integer updateDicValue(DicValue dicValue);

    @Delete("""
            delete from t_dic_value where id = #{id}
            """)
    Integer deleteDicValueById(Integer id);
}
