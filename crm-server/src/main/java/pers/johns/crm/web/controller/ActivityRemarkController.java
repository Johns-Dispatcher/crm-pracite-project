package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 新增活动备注方法
     * @param viewActivityRemark 视图活动对象
     * @param authentication 认证信息
     * @return 含有成功信息的响应结果对象
     */
    @PostMapping("/")
    public HttpResult addActivityRemark(
            @RequestBody
            ViewActivityRemark viewActivityRemark,
            Authentication authentication
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewActivityRemark.setCreateBy(securityUser.getId());
        viewActivityRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("添加活动备注成功", activityRemarkService.addActivityRemark(viewActivityRemark));
    }

    /**
     * 获取活动备注信息
     * @param id 活动 id
     * @param page 当前页数
     * @return 含有分页信息的响应结果对象
     */
    @GetMapping("/{id}/{page}")
    public HttpResult getActivityRemark(
            @PathVariable("id")
            Integer id,
            @PathVariable("page")
            Integer page
    ) {
        PageInfo<Object> pageInfo = activityRemarkService.getActivityRemarksByPage(id, page);

        return HttpResult.OK("查询备注成功", pageInfo);
    }

    /**
     * 修改活动备注信息
     * @param viewActivityRemark 视图活动备注信息对象
     * @param authentication 认证信息
     * @return 含有修改成功信息的响应结果对象
     */
    @PutMapping("/")
    public HttpResult editActivityRemark(
            @RequestBody
            ViewActivityRemark viewActivityRemark,
            Authentication authentication)
    {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewActivityRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("修改活动备注成功", activityRemarkService.editActivityRemark(viewActivityRemark));
    }

    /**
     * 删除活动备注信息
     * @param id 活动备注信息
     * @return 含有删除成功信息的响应结果对象
     */
    @DeleteMapping("/{id}")
    public HttpResult deleteActivityRemark(@PathVariable("id") Integer id) {
        return HttpResult.OK("删除活动备注成功", activityRemarkService.deleteActivityRemark(id));
    }
}
