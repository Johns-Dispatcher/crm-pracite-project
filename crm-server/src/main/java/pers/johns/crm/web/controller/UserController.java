package pers.johns.crm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.service.UserService;

import java.util.Map;

/**
 * ClassName    : UserController
 * <br/>
 * Description  : 用于处理用户访问请求的控制器
 * <br/>
 * CreateTime   : 2024/6/27 12:31
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取认证信息
     * @param authentication 用户认证信息
     * @return 携带有用户信息的 {@link HttpResult} 对象
     */
    @GetMapping("/api/login/info")
    public HttpResult loginInfo(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return HttpResult.OK(securityUser);
    }

    /**
     * 免登录接口，能处理到这一定是通过了认证
     * @return 携带有登录成功信息的 {@link HttpResult} 对象
     */
    @GetMapping("/api/login/free")
    public HttpResult loginFree() {
        return HttpResult.OK("免登录成功");
    }

    /**
     * 续期接口，用于前端用户进行续签，能处理到这一定是通过了认证
     * @param authentication 用户认证信息
     * @return 携带有续签信息的 {@link HttpResult} 对象
     */
    @GetMapping("/api/login/renewal")
    public HttpResult loginRenewal(Authentication authentication) {
        Map<String, Object> data = userService.renewalToken(authentication);
        return HttpResult.OK("续签成功", data);
    }
}
