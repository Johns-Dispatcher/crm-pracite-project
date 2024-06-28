package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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

@Data
// json 反序列化必须提供无参构造
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityUser implements UserDetails, Serializable {

    @NonNull
    private User user;
    /**
     * 这里不能直接存储 SimpleGrantedAuthority 集合
     * 反序列化会出现问题
     * 如果直接让序列化 User 中的 Role 可能导致请求头过大
     */
    private List<String> authorityList;
    /**
     * 认证过期时间，交给前端进行续期判断
     */
    private Long expireTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 第一次调用一定会调用这个获取权限，此时进行集合的初始化
        // 之后的访问会从 JWT 中反序列化出数据存储在集合当中
        if (authorityList == null) {
            authorityList = user.getRoles().stream()
                    .map(Role::getPermissions)
                    .flatMap(List::stream)
                    .map(Permission::getCode)
                    // 去空 权限为空会报错
                    .filter(s -> s != null && !s.isEmpty())
                    .toList();
        }

        return authorityList.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        String loginPwd = user.getLoginPwd();
        user.setLoginPwd(null);
        return loginPwd;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getLoginAct();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return user.getAccountNoExpired() == 1;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return user.getAccountNoLocked() == 1;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNoExpired() == 1;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return user.getAccountEnabled() == 1;
    }
}
