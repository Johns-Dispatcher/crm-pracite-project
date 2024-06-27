package pers.johns.crm.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;

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
public class UserController {
    @GetMapping("/api/login/info")
    public HttpResult loginInfo(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return HttpResult.OK(securityUser);
    }
}