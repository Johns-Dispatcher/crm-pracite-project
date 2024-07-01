package pers.johns.crm.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName    : DataFilterQuery
 * <br/>
 * Description  : 用于对指定 SQL 语句额外添加过滤条件
 * <br/>
 * CreateTime   : 2024/7/1 15:27
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataFilterQuery {
    /**
     * 用于过滤的 SQL 语句
     */
    private String filterSQL;
}
