package pers.johns.crm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.service.UserService;

import java.util.Map;

/**
 * ClassName    : LoginController
 * <br/>
 * Description  : 用于处理登录相关业务的控制器
 * <br/>
 * CreateTime   : 2024/6/28 18:34
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    /**
     * 获取认证信息
     * @param authentication 用户认证信息
     * @return 携带有用户信息的 {@link HttpResult} 对象
     */
    @GetMapping("/info")
    public HttpResult loginInfo(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        ViewUser viewUser = ViewUser.builder()
                .id(securityUser.getId())
                .name(securityUser.getName())
                .build();
        viewUser.setExpireTime(securityUser.getExpireTime());
        viewUser.setAuthentications(securityUser.getAuthorityList());

        return HttpResult.OK("用户信息", viewUser);
    }

    /**
     * 免登录接口，能处理到这一定是通过了认证
     * @return 携带有登录成功信息的 {@link HttpResult} 对象
     */
    @GetMapping("/free")
    public HttpResult loginFree() {
        return HttpResult.OK("免登录成功");
    }

    /**
     * 续期接口，用于前端用户进行续签，能处理到这一定是通过了认证
     * @param authentication 用户认证信息
     * @return 携带有续签信息的 {@link HttpResult} 对象
     */
    @GetMapping("/renewal")
    public HttpResult loginRenewal(Authentication authentication) {
        Map<String, Object> data = userService.renewalToken(authentication);
        return HttpResult.OK("续签成功", data);
    }
}
