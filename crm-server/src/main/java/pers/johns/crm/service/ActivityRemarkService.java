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
    /**
     * 新增活动备注
     * @param viewActivityRemark 视图活动备注
     * @return 是否增加活动备注
     */
    Boolean addActivityRemark(ViewActivityRemark viewActivityRemark);

    /**
     * 分页查询活动备注信息
     * @param id 活动 id
     * @param page 页数
     * @return 含有分页信息的活动列表
     */
    PageInfo<Object> getActivityRemarksByPage(Integer id, Integer page);

    /**
     * 编辑活动备注
     * @param viewActivityRemark 视图活动备注对象
     * @return 是否编辑成功
     */
    Boolean editActivityRemark(ViewActivityRemark viewActivityRemark);

    /**
     * 删除活动备注，采用逻辑删除，将数据库删除位置为空
     * @param id 活动备注 id
     * @return 是否删除成功
     */
    Boolean deleteActivityRemark(Integer id);
}
