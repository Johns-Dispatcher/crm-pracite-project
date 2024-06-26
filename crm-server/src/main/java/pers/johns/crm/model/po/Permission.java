package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName    : Permission
 * <br/>
 * Description  : 用户权限类，对应数据库 t_permission
 * <br/>
 * CreateTime   : 2024/6/24 19:46
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {
    private Integer id;
    private String name;
    private String code;
    private String url;
    private String type;
    private Integer parentId;
    private Integer orderNo;
    private String icon;
}
