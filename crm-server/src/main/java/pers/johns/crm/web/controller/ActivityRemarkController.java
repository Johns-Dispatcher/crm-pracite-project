package pers.johns.crm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewActivityRemark;
import pers.johns.crm.service.ActivityRemarkService;

/**
 * ClassName    : ActivityRemarkController
 * <br/>
 * Description  : 活动备注控制器
 * <br/>
 * CreateTime   : 2024/7/3 23:57
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequestMapping("/api/activity/remark")
@RequiredArgsConstructor
public class ActivityRemarkController {

    private final ActivityRemarkService activityRemarkService;

    @PostMapping("/")
    public HttpResult addActivityRemark(@RequestBody ViewActivityRemark viewActivityRemark, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewActivityRemark.setCreateBy(securityUser.getId());
        viewActivityRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("添加活动备注成功", activityRemarkService.addActivityRemark(viewActivityRemark));
    }
}
