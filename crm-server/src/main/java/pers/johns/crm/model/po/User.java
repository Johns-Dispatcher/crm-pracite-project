package pers.johns.crm.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    /**
     * 数据库用户 id
     */
    private Integer id;
    /**
     * 用户登录账户名
     */
    private String loginAct;
    /**
     * 用户登录密码密文
     */
    private String loginPwd;
    /**
     * 用户姓名/昵称
     */
    private String name;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户是否为过期 1 = 未过期 0 = 过期
     */
    private Integer accountNoExpired;
    /**
     * 用户是否未锁定 1 = 未锁定 0 = 锁定
     */
    private Integer accountNoLocked;
    /**
     * 用户是否启用 1 = 启用 0 = 未启用
     */
    private Integer accountEnabled;
    /**
     * 用户凭证是否过期 1 = 未过期 0 = 过期
     */
    private Integer credentialsNoExpired;
    /**
     * 用户创建时间
     */
    private LocalDateTime createTime;
    /**
     * 用户由谁创建
     */
    private Integer createBy;
    /**
     * 用户最后编辑时间
     */
    private LocalDateTime editTime;
    /**
     * 用户由谁最后编辑
     */
    private Integer editBy;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 用户角色信息
     */
    @JsonIgnore
    private List<Role> roles;
}
