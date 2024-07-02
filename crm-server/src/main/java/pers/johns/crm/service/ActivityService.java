package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.model.vo.ViewActivity;

import java.util.List;
import java.util.Map;

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

    List<ViewActivity> getActivityOwners();
}
