package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.ActivityQuery;
import pers.johns.crm.service.ActivityService;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName    : ActivityController
 * <br/>
 * Description  : 处理活动相关业务的控制器
 * <br/>
 * CreateTime   : 2024/7/1 21:21
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityService activityService;

    /**
     * 根据页面分页查询活动信息
     * @param currentPage 当前页码
     * @return 含有分页信息的响应结果对象
     */
    @GetMapping("/page/{page}")
    public HttpResult getActivitiesByPage(@PathVariable(value = "page", required = false) Integer currentPage) {
        PageInfo<Object> activitiesByPage = activityService.getActivitiesByPage(currentPage == null ? 1 : currentPage);
        return HttpResult.OK("获取活动成功", activitiesByPage);
    }

    /**
     * 获取当前负责活动的用户，只获取有活动的用户
     * @return 含有用户名信息的响应结果
     */
    @GetMapping("/owner")
    public HttpResult getActivityOwners() {
        return HttpResult.OK("获取活动负责人成功", activityService.getActivityOwners());
    }

    /**
     * 按照条件进行搜索，同时进行分页查询<br/>
     * （似乎可以与分页产线合并...）
     * @param activitySearchQuery 筛选信息
     * @return 含有分页信息的响应结果对象
     */
    @PostMapping("/search")
    public HttpResult searchActivities(@RequestBody ActivityQuery activitySearchQuery) {
        PageInfo<Object> pageInfo = activityService.searchActivitiesByPage(activitySearchQuery);
        return HttpResult.OK("搜索查询活动成功", pageInfo);
    }

    /**
     * 新增活动信息
     * @param viewActivity 前端提供的活动信息
     * @param authentication 当前登录的认证信息
     * @return 含有成功信息的响应结果对象
     */
    @PostMapping("/")
    public HttpResult addActivity(@RequestBody ViewActivity viewActivity, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewActivity.setCreateBy(securityUser.getId());
        viewActivity.setEditBy(securityUser.getId());

        return HttpResult.OK("添加活动成功", activityService.addActivity(viewActivity));
    }

    /**
     * 通过 id 获取指定活动信息
     * @param id 活动 id
     * @return 含有活动信息响应结果对象
     */
    @GetMapping("/{id}")
    public HttpResult getActivity(@PathVariable("id") Integer id) {
        return HttpResult.OK("获取指定活动信息", activityService.getActivity(id));
    }

    /**
     * 修改活动信息
     * @param viewActivity 活动信息
     * @param authentication 登录的认证信息
     * @return 含有成功信息的响应结果对象
     */
    @PutMapping("/")
    public HttpResult editActivity(@RequestBody ViewActivity viewActivity, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewActivity.setEditBy(securityUser.getId());

        return HttpResult.OK("修改活动成功", activityService.editActivity(viewActivity));
    }

    /**
     * 删除活动信息
     * @param id 活动 id
     * @return 含有成功信息的响应结果对象
     */
    @DeleteMapping("/{id}")
    public HttpResult deleteActivity(@PathVariable("id") Integer id) {
        return HttpResult.OK("删除活动及其备注成功", activityService.deleteActivity(id));
    }

    /**
     * 批量删除活动信息
     * @param ids 活动 id 串，要求使用 "-" 进行分割
     * @return 含有成功信息的响应结果对象
     */
    @DeleteMapping("/bulk/{ids}")
    public HttpResult deleteBulkActivity(@PathVariable("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split("-")).map(Integer::parseInt).toList();
        return HttpResult.OK("批量删除活动及其备注成功", activityService.deleteBulkActivity(idList));
    }
}
