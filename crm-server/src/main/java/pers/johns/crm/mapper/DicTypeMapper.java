package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.DicType;

import java.util.List;

/**
 * InterfaceName : DicTypeMapper
 * <br/>
 * Description   : 用于操作 t_dic_type 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 23:45
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface DicTypeMapper {

    @Select("""
            select id, type_code, type_name, remark
            from t_dic_type
            where id = #{id}
            """)
    @Results(id = "DicTypeBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "type_code", property = "typeCode"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "remark", property = "remark")
    })
    DicType selectById(Integer id);

    @Select("""
            select id, type_code, type_name, remark
            from t_dic_type
            """)
    @ResultMap("DicTypeBaseMap")
    List<DicType> selectAll();

    @Insert("""
            insert into t_dic_type (
                id, type_code, type_name, remark
            ) values (
                null, #{typeCode}, #{typeName}, #{remark}
            )
            """)
    Integer insertDicType(DicType dicType);

    @Update("""
            update t_dic_type set
                type_code = #{typeCode}, type_name = #{typeName},
                remark = #{remark}
            where id = #{id}
            """)
    Integer updateDicType(DicType dicType);

    @Delete("""
            delete from t_dic_type where id = #{id}
            """)
    Integer deleteDicTypeById(Integer id);
}
