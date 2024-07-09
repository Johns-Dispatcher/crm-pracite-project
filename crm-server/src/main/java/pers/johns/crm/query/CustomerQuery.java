package pers.johns.crm.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClassName    : CustomerQuery
 * <br/>
 * Description  : 客户查询对象
 * <br/>
 * CreateTime   : 2024/7/9 10:24
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerQuery extends DataFilterQuery{
    private Integer currentPage;
}
