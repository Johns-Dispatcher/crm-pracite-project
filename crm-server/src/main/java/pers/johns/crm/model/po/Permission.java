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
    /**
     * 权限信息 id
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限名
     */
    private String code;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限类型
     */
    private String type;
    /**
     * 权限父 id
     */
    private Integer parentId;
    private Integer orderNo;
    private String icon;
}
