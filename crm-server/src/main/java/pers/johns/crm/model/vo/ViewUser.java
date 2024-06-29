package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.johns.crm.model.po.Permission;
import pers.johns.crm.model.po.Role;
import pers.johns.crm.model.po.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : ViewUser
 * <br/>
 * Description  : 视图用户对象，对用户对象进行了筛选，用于前端信息展示
 * <br/>
 * CreateTime   : 2024/6/28 20:08
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@NoArgsConstructor
@Data
public class ViewUser {
    private Integer id;
    private String loginAct;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer createBy;
    private String creatorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime editTime;
    private Integer editBy;
    private String editorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;
    private Boolean accountNoExpired;
    private Boolean accountNoLocked;
    private Boolean accountEnabled;
    private Boolean credentialsNoExpired;
    private List<String> authentications;

    public ViewUser(User user) {
        this.id = user.getId();
        this.loginAct = user.getLoginAct();
        this.name = user.getName();
        this.phone= user.getPhone();
        this.email = user.getEmail();
        this.createTime = user.getCreateTime();
        this.createBy = user.getCreateBy();
        this.editTime = user.getEditTime();
        this.editBy = user.getEditBy();
        this.lastLoginTime = user.getLastLoginTime();
        this.accountNoExpired = user.getAccountNoExpired() == 1;
        this.accountNoLocked = user.getAccountNoLocked() == 1;
        this.accountEnabled = user.getAccountEnabled() == 1;
        this.credentialsNoExpired = user.getCredentialsNoExpired() == 1;
        this.authentications = user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(List::stream)
                .map(Permission::getCode)
                .filter(code -> code != null && !code.isEmpty())
                .toList();
    }

    // 这个构造方法是为了在分页查询时进行映射转换使用的
    public ViewUser(Object user) {
        this((User) user);
    }
}
