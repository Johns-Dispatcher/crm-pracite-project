package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName    : DicTypeConst
 * <br/>
 * Description  : 字典类型类，对应数据库 t_dic_type
 * <br/>
 * CreateTime   : 2024/6/25 23:37
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DicType {
    private Integer id;
    private String typeCode;
    private String typeName;
    private String remark;
}
