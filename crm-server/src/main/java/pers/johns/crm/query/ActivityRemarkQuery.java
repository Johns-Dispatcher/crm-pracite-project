package pers.johns.crm.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClassName    : ActivityRemarkQuery
 * <br/>
 * Description  : 用于查询活动备注的过滤参数
 * <br/>
 * CreateTime   : 2024/7/4 0:53
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRemarkQuery extends DataFilterQuery{
    private Integer activityId;
}
