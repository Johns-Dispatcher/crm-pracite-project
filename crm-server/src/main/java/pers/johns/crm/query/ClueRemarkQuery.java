package pers.johns.crm.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClassName    : ClueRemarkQuery
 * <br/>
 * Description  : 线索跟进查询对象
 * <br/>
 * CreateTime   : 2024/7/8 16:14
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClueRemarkQuery extends DataFilterQuery{
    private Integer currentPage;
    private Integer clueId;
}
