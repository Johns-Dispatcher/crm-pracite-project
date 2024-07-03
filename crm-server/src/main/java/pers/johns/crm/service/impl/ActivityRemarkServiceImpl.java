package pers.johns.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.johns.crm.exception.ActivityRemarkException;
import pers.johns.crm.mapper.ActivityRemarkMapper;
import pers.johns.crm.model.po.ActivityRemark;
import pers.johns.crm.model.vo.ViewActivityRemark;
import pers.johns.crm.service.ActivityRemarkService;

import java.time.LocalDateTime;

/**
 * ClassName    : ActivityRemarkServiceImpl
 * <br/>
 * Description  : 活动备注业务实现类
 * <br/>
 * CreateTime   : 2024/7/3 23:44
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    private final ActivityRemarkMapper activityRemarkMapper;

    @Override
    public Boolean addActivityRemark(ViewActivityRemark viewActivityRemark) {
        ActivityRemark activityRemark = ActivityRemark
                .builder()
                .activityId(viewActivityRemark.getActivityId())
                .noteContent(viewActivityRemark.getNoteContent())
                .createBy(viewActivityRemark.getCreateBy())
                .createTime(LocalDateTime.now())
                .editBy(viewActivityRemark.getEditBy())
                .editTime(LocalDateTime.now())
                .deleted(0)
                .build();

        Integer count = activityRemarkMapper.insertActivityRemark(activityRemark);

        if (count != 1) throw new ActivityRemarkException("添加活动备注失败");

        return true;
    }
}
