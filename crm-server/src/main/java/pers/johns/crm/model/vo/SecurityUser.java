package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.johns.crm.model.po.Role;
import pers.johns.crm.model.po.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * ClassName    : SecurityUser
 * <br/>
 * Description  : 安全用户对象，存储从数据库获取的用户对象，同时擦除密码
 * <br/>
 * CreateTime   : 2024/6/26 10:23
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@NoArgsConstructor
// json 反序列化必须提供无参构造
public class SecurityUser implements UserDetails, Serializable {

    private Integer id;
    private String loginAct;
    private String loginPwd;
    private String name;
    private Boolean accountNoExpired;
    private Boolean accountNoLocked;
    private Boolean accountEnabled;
    private Boolean credentialsNoExpired;
    /**
     * 这里不能直接存储 SimpleGrantedAuthority 集合
     * 反序列化会出现问题
     * 如果直接让序列化 User 中的 Role 可能导致请求头过大
     */
    private List<String> authorityList;
    /**
     * 角色列表
     */
    private List<String> roleList;
    /**
     * 认证过期时间，交给前端进行续期判断
     */
    private Long expireTime;

    /**
     * 最开始构造 SecurityUser 对象时调用的构造方法
     * @param user {@link User} 对象
     */
    public SecurityUser(User user) {
        this.id = user.getId();
        this.loginAct = user.getLoginAct();
        this.loginPwd = user.getLoginPwd();
        this.name = user.getName();
        this.accountEnabled = user.getAccountEnabled() == 1;
        this.accountNoLocked = user.getAccountNoLocked() == 1;
        this.accountNoExpired = user.getAccountNoExpired() == 1;
        this.credentialsNoExpired = user.getCredentialsNoExpired() == 1;

        authorityList = user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(List::stream)
                .map(permission -> {
                    if (permission.getCode() != null) {
                        return permission.getCode();
                    } else if (permission.getUrl() != null) {
                        return permission.getUrl();
                    } else {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .filter(authority -> !authority.isEmpty())
                .toList();

        roleList = user.getRoles().stream()
                .map(Role::getRole).toList();
    }

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 首次调用会帮助初始化权限列表和角色列表
     * 之后反序列化回来可以之间获取权限列表
     * @return 权限列表
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        String loginPwd = this.loginPwd;
        this.loginPwd = null;
        return loginPwd;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return loginAct;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return accountNoExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return credentialsNoExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return accountEnabled;
    }
}
