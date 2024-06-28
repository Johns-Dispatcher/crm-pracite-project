package pers.johns.crm.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * InterfaceName : UserService
 * <br/>
 * Description   : 用户业务结果，处理登录业务
 * <br/>
 * CreateTime    : 2024/6/26 10:16
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface UserService extends UserDetailsService {
    /**
     * 为指定用户进行续期
     * @param authentication 用户认证对象
     * @return 续期结果的 Map 用于向前端发送数据，含有以下项 <br/>
     *   token - 新生成的 Token
     *   expireTime - 新生成 Token 的过期时间
     */
    Map<String, Object> renewalToken(Authentication authentication);
}
