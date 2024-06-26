package pers.johns.crm.model.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.johns.crm.model.po.Permission;
import pers.johns.crm.model.po.Role;
import pers.johns.crm.model.po.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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

public record SecurityUser(User user) implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(List::stream)
                .map(Permission::getCode)
                // 去空 权限为空会报错
                .filter(s -> s != null && !s.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        String loginPwd = user.getLoginPwd();
        user.setLoginPwd(null);
        return loginPwd;
    }

    @Override
    public String getUsername() {
        return user.getLoginAct();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNoExpired() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNoLocked() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNoExpired() == 1;
    }

    @Override
    public boolean isEnabled() {
        return user.getAccountEnabled() == 1;
    }
}
