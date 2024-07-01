package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.service.ActivityService;

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
        log.info("正在获取活动数据，页码: {}", currentPage);
        PageInfo<Object> activitiesByPage = activityService.getActivitiesByPage(currentPage == null ? 1 : currentPage);
        return HttpResult.OK("获取活动成功", activitiesByPage);
    }
}
