package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.service.UserService;


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
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 分页查询
     * @param current 查询页数
     * @return 携带分页信息的响应结果
     */
    @GetMapping("/page/{page}")
    public HttpResult getUsersByPage(
            @PathVariable(value = "page", required = false) Integer current
    ) {
        PageInfo<Object> usersByPage = userService.getUserByPage(current == null ? 1 : current);
        return HttpResult.OK(usersByPage);
    }

    /**
     * 按照登录名查询用户信息
     * @param loginAct 登录名称
     * @return 含有用户信息的响应结果对象
     */
    @GetMapping("/{loginAct}")
    public HttpResult getUserByLoginAct(@PathVariable("loginAct") String loginAct) {
        ViewUser viewUser = userService.getUserByLoginAct(loginAct);
        if (viewUser == null) {
            return HttpResult.Fail("查询失败");
        }
        else {
            return HttpResult.OK("查询成功", viewUser);
        }
    }

    /**
     * 根据登录名查询数据库中是否存在该用户
     * @param loginAct 登录名称
     * @return 含有是否存在信息的响应结果对象
     */
    @GetMapping("/checkAct/{loginAct}")
    public HttpResult checkLoginAct(@PathVariable("loginAct") String loginAct) {
        Boolean result = userService.checkLoginAct(loginAct);
        if (result) {
            return HttpResult.OK("用户存在", true);
        }
        else {
            return HttpResult.OK("用户不存在", false);
        }
    }

    /**
     * 根据前端数据新增用户
     * @param viewUser 视图用户，用于接收前端数据
     * @param authentication 认证信息，记录谁增加的用户
     * @return 响应结果对象，能够执行完成表示没有出错
     * 接收 FromData 对象不能使用 @RequestBody 进行接收
     */
    @PostMapping("")
    public HttpResult addUser(ViewUser viewUser, Authentication authentication) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        // 为用户增加创建者信息
        viewUser.setCreateBy(securityUser.getUser().getId());
        viewUser.setEditBy(securityUser.getUser().getId());

        Boolean result = userService.addUser(viewUser);

        return HttpResult.OK("添加成功", result);
    }

    @PutMapping("")
    public HttpResult editUser(ViewUser viewUser, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        // 为用户增加创建者信息
        viewUser.setCreateBy(securityUser.getUser().getId());
        viewUser.setEditBy(securityUser.getUser().getId());
        viewUser.setLoginPwd("undefined".equals(viewUser.getLoginPwd()) ? null : viewUser.getLoginPwd());

        log.info(viewUser.toString());
        Boolean result = userService.editUser(viewUser);

        return HttpResult.OK("修改成功", result);
    }
}
