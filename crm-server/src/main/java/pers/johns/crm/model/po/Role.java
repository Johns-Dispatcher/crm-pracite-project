package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName    : Role
 * <br/>
 * Description  : 用户角色信息类，对应数据库 t_role
 * <br/>
 * CreateTime   : 2024/6/24 18:17
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    /**
     * 角色 id
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String role;
    /**
     * 角色中文名称/别名
     */
    private String roleName;
    /**
     * 角色拥有权限
     */
    private List<Permission> permissions;
}
