package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.model.vo.ViewActivityRemark;

/**
 * InterfaceName : ActivityRemarkService
 * <br/>
 * Description   : 活动备注业务接口
 * <br/>
 * CreateTime    : 2024/7/3 23:44
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ActivityRemarkService {
    Boolean addActivityRemark(ViewActivityRemark viewActivityRemark);

    PageInfo<Object> getActivityRemarksByPage(Integer id, Integer page);

    Boolean editActivityRemark(ViewActivityRemark viewActivityRemark);

    Boolean deleteActivityRemark(Integer id);
}
