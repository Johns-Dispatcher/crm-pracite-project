package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : User
 * <br/>
 * Description  : 用户对象，对应数据库表 t_user
 * <br/>
 * CreateTime   : 2024/6/24 14:33
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String loginAct;
    private String loginPwd;
    private String name;
    private String phone;
    private String email;
    private Integer accountNoExpired;
    private Integer accountNoLocked;
    private Integer accountEnabled;
    private Integer credentialsNoExpired;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private LocalDateTime lastLoginTime;
    private List<Role> roles;
}
