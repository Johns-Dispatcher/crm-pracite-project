package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.ActivityQuery;

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

    /**
     * 带有搜索条件的分页查询
     * @param activitySearchQuery 搜索信息
     * @return 含有分页信息的活动列表
     */
    PageInfo<Object> searchActivitiesByPage(ActivityQuery activitySearchQuery);

    /**
     * 添加活动
     * @param viewActivity 视图活动对象
     * @return 是否添加成功
     */
    Boolean addActivity(ViewActivity viewActivity);

    /**
     * 获取活动信息
     * @param id 活动 id
     * @return 视图活动对象
     */
    ViewActivity getActivity(Integer id);

    /**
     * 修改活动信息
     * @param viewActivity 视图活动对象
     * @return 是否修改成功
     */
    Boolean editActivity(ViewActivity viewActivity);

    /**
     * 删除活动信息
     * @param id 活动 id
     * @return 是否删除成功
     */
    Boolean deleteActivity(Integer id);

    /**
     * 批量删除活动
     * @param ids 活动 id 列表
     * @return 是否批量删除成功
     */
    Boolean deleteBulkActivity(List<Integer> ids);
}
