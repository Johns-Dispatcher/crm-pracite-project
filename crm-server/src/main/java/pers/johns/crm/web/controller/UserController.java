package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
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
public class UserController {

    private final UserService userService;

    /**
     * 分页查询
     * @param current 查询页数
     * @return
     */
    @GetMapping("/api/user/page/{page}")
    public HttpResult getUsersByPage(
            @PathVariable(value = "page", required = false) Integer current
    ) {
        PageInfo<Object> usersByPage = userService.getUserByPage(current == null ? 1 : current);
        return HttpResult.OK(usersByPage);
    }
}
