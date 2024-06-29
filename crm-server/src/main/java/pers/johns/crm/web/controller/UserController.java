package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.service.UserService;

import java.util.List;
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
@RequestMapping("/api/user")
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
    public HttpResult getUserByLoginAct(@PathVariable(value = "loginAct") String loginAct) {
        ViewUser viewUser = userService.getUserByLoginAct(loginAct);
        if (viewUser == null) return HttpResult.Fail("查询失败");
        else return HttpResult.OK("查询成功", viewUser);
    }
}
