package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import pers.johns.crm.model.po.Clue;

import java.util.List;

/**
 * InterfaceName : ClueMapper
 * <br/>
 * Description   : 用于操作 t_clue 的 Dao
 * <br/>
 * CreateTime    : 2024/6/25 10:13
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ClueMapper {
    @Select("""
            select
                id, owner_id, activity_id,
                full_name, appellation,
                phone, wechat, qq, email,
                age, job, year_income,
                address, need_loan,
                intention_state, intention_product,
                state, source,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_clue
            where id = #{id}
            """)
    @Results(id = "ClueBaseMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "owner_id", property = "ownerId"),
            @Result(column = "activity_id", property = "activityId"),
            @Result(column = "full_name", property = "fullName"),
            @Result(column = "appellation", property = "appellation"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "wechat", property = "wechat"),
            @Result(column = "qq", property = "qq"),
            @Result(column = "email", property = "email"),
            @Result(column = "age", property = "age"),
            @Result(column = "job", property = "job"),
            @Result(column = "year_income", property = "yearIncome"),
            @Result(column = "address", property = "address"),
            @Result(column = "need_loan", property = "needLoan"),
            @Result(column = "intention_state", property = "intentionState"),
            @Result(column = "intention_product", property = "intentionProduct"),
            @Result(column = "state", property = "state"),
            @Result(column = "source", property = "source"),
            @Result(column = "description", property = "description"),
            @Result(column = "next_contact_time", property = "nextContactTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "create_by", property = "createBy"),
            @Result(column = "edit_time", property = "editTime"),
            @Result(column = "edit_by", property = "editBy"),
            @Result(column = "id", property = "clueRemarks", one = @One(
                    select = "pers.johns.crm.mapper.ClueRemarkMapper.selectClueRemarksByClueId"
            ))
    })
    Clue selectById(Integer id);

    @Select("""
            select
                id, owner_id, activity_id,
                full_name, appellation,
                phone, wechat, qq, email,
                age, job, year_income,
                address, need_loan,
                intention_state, intention_product,
                state, source,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by
            from t_clue
            """)
    @ResultMap("ClueBaseMap")
    List<Clue> selectAll();

    @Insert("""
            insert into t_clue (
                id, owner_id, activity_id,
                full_name, appellation,
                phone, wechat, qq, email,
                age, job, year_income,
                address, need_loan,
                intention_state, intention_product,
                state, source,
                description, next_contact_time,
                create_time, create_by,
                edit_time, edit_by)
            values (
                null, #{id}, #{ownerId},
                #{fullName}, #{appellation},
                #{phone}, #{wechat}, #{qq}, #{email},
                #{age}, #{job}, #{yearIncome},
                #{address}, #{needLoan},
                #{intentionState}, #{intentionProduct},
                #{state}, #{source},
                #{description}, #{nextContact},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertClue(Clue clue);

    @Update("""
            update t_clue
            set
                owner_id = #{ownerId}, activity_id = #{activityId},
                full_name = #{fullName}, appellation = #{appellation},
                phone = #{phone}, wechat = #{wechat}, qq = #{qq}, email = #{email},
                age = #{age}, job = #{job}, year_income = #{yearIncome},
                address = #{address}, need_loan = #{needLoan},
                intention_state = #{intentionState}, intention_product = #{intentionProduct},
                state = #{state}, source = #{source},
                description = #{description}, next_contact_time = #{nextContactTime},
                create_time = #{createTime}, create_by = #{createBy},
                edit_time = #{editTime}, edit_by = #{editBy}
            where id = #{id}
            """)
    Integer updateClue(Clue clue);

    @Delete("""
            delete from t_clue where id = #{id}
            """)
    Integer deleteClueById(Integer id);
}
