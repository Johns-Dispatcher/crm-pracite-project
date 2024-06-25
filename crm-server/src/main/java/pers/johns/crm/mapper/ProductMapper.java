package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.mode.po.Product;

import java.util.List;

/**
 * InterfaceName : ProductMapper
 * <br/>
 * Description   : 用于操作 t_product 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 21:13
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ProductMapper {
    @Select("""
            select
                id, name,
                guide_price_s, guide_price_e,
                quotation, state,
                create_time, create_by,
                edit_time, edit_by
            from t_product
            where id = #{id}
            """)
    @Results(id = "ProductBaseMap", value = {
            @Result(column = "id", property = "id",  id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "guide_price_s", property = "guidePriceS"),
            @Result(column = "guide_price_e", property = "guidePriceE"),
            @Result(column = "quotation", property = "quotation"),
            @Result(column = "state", property = "state"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
    })
    Product selectById(Integer id);

    @Select("""
            select
                id, name,
                guide_price_s, guide_price_e,
                quotation, state,
                create_time, create_by,
                edit_time, edit_by
            from t_product
            """)
    @ResultMap("ProductBaseMap")
    List<Product> selectAll();

    @Insert("""
            insert into t_product (
                id, name,
                guide_price_s, guide_price_e,
                quotation, state,
                create_time, create_by,
                edit_time, edit_by
            ) values (
                null, #{name},
                #{guidePriceS}, #{guidePriceE},
                #{quotation}, #{state},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertProduct(Product product);

    @Update("""
            update t_product
            set
                name = #{name},
                guide_price_s = #{guidePriceS}, guide_price_e = #{guidePriceE},
                quotation = #{quotation}, state = #{state},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateProduct(Product product);

    @Delete("""
            delete from t_product where id = #{id}
            """)
    Integer deleteProductById(Integer id);
}
