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
    private Integer id;
    private String role;
    private String roleName;
    private List<Permission> permissions;
}
