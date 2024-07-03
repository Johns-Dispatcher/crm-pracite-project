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

import java.util.Arrays;
import java.util.List;


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
        viewUser.setCreateBy(securityUser.getId());
        viewUser.setEditBy(securityUser.getId());

        Boolean result = userService.addUser(viewUser);

        return HttpResult.OK("添加成功", result);
    }

    /**
     * 修改用户信息
     * @param viewUser 接收前端传递信息
     * @param authentication 获取当前登录用户信息
     * @return 含有修改结果的响应结果信息
     */
    @PutMapping("")
    public HttpResult editUser(ViewUser viewUser, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        // 为用户增加修改者信息
        viewUser.setEditBy(securityUser.getId());
        // undefined 视为未传入，置为空值
        viewUser.setLoginPwd("undefined".equals(viewUser.getLoginPwd()) ? null : viewUser.getLoginPwd());

        Boolean result = userService.editUser(viewUser);

        return HttpResult.OK("修改成功", result);
    }

    /**
     * 根据 id 删除对应用户
     * @param id 用户 id
     * @return 含有删除结果的响应结果信息
     */
    @DeleteMapping("/{id}")
    public HttpResult deleteUser(@PathVariable("id") Integer id) {
        Boolean result = userService.deleteUser(id);
        return HttpResult.OK("删除成功", result);
    }

    /**
     * 批量删除用户信息
     * @param ids 要删除的 id 列表，要求前端按照 - 进行分割字符
     * @return 含有删除结果的响应信息
     */
    @DeleteMapping("/bulk/{ids}")
    public HttpResult bulkDeleteUsers(@PathVariable("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split("-")).map(Integer::parseInt).toList();
        Boolean result = userService.deleteUsersByIds(idList);
        return HttpResult.OK("批量删除成功", result);
    }

    /**
     * 获取目前的所有用户名以及 id 信息
     * @return 含有用户名信息的列表
     */
    @GetMapping("/name")
    public HttpResult getUserWithName() {
        List<ViewUser> userList = userService.getUserWithName();
        return HttpResult.OK("获取用户名信息成功", userList);
    }
}
