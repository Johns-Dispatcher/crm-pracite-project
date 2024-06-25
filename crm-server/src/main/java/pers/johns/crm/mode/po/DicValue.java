package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName    : DicValue
 * <br/>
 * Description  : 字典值类，对应数据库 t_dic_value
 * <br/>
 * CreateTime   : 2024/6/25 23:14
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DicValue {
    private Integer id;
    private String typeCode;
    private String typeValue;
    private Integer order;
    private String remark;
}
