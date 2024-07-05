package pers.johns.crm.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClassName    : ClueQuery
 * <br/>
 * Description  : 线索查询对象，用于查询条件的封装
 * <br/>
 * CreateTime   : 2024/7/4 22:16
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClueQuery extends DataFilterQuery{
    // 当前页数
    private Integer currentPage;
}
