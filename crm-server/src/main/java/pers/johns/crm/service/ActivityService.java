package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.ActivitySearchQuery;

import java.util.List;

/**
 * InterfaceName : ActivityService
 * <br/>
 * Description   : 活动业务接口
 * <br/>
 * CreateTime    : 2024/7/1 21:35
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ActivityService {
    /**
     * 分页查询活动信息
     * @param currentPage 当前页数
     * @return 含有分页信息的活动列表
     */
    PageInfo<Object> getActivitiesByPage(Integer currentPage);

    /**
     * 查询目前负责项目的项目人列表
     * @return 视图活动 {@link ViewActivity} 列表，仅含有负责人与负责人 ID
     */
    List<ViewActivity> getActivityOwners();

    PageInfo<Object> searchActivitiesByPage(ActivitySearchQuery activitySearchQuery);

    Boolean addActivity(ViewActivity viewActivity);

    ViewActivity getActivity(Integer id);

    Boolean editActivity(ViewActivity viewActivity);

    Boolean deleteActivity(Integer id);

    Boolean deleteBulkActivity(List<Integer> ids);
}
