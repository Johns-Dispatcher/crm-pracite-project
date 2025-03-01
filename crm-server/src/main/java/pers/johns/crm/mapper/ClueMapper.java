package pers.johns.crm.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import pers.johns.crm.annotation.DataScope;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.query.ClueQuery;

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
                    select = "pers.johns.crm.mapper.ClueRemarkMapper.selectClueRemarksByClueId",
                    fetchType = FetchType.LAZY
            ))
    })
    Clue selectById(Integer id);

    @DataScope(tableField = "owner_id")
    List<Clue> selectAll(ClueQuery clueQuery);

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
                null, #{ownerId}, #{activityId},
                #{fullName}, #{appellation},
                #{phone}, #{wechat}, #{qq}, #{email},
                #{age}, #{job}, #{yearIncome},
                #{address}, #{needLoan},
                #{intentionState}, #{intentionProduct},
                #{state}, #{source},
                #{description}, #{nextContactTime},
                #{createTime}, #{createBy},
                #{editTime}, #{editBy}
            )
            """)
    Integer insertClue(Clue clue);

    Integer insertBatchClues(List<Clue> clues);

    @Select("""
            select count(1) from t_clue where phone = #{phone}
            """)
    Integer selectByPhone(String phone);

    Integer updateClue(Clue clue);

    @Delete("""
            delete from t_clue where id = #{id}
            """)
    Integer deleteClueById(Integer id);

    Integer deleteBulkCluesBuId(List<Integer> ids);
}
