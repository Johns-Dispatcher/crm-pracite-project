package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ActivityRemarkException;
import pers.johns.crm.mapper.ActivityRemarkMapper;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.ActivityRemark;
import pers.johns.crm.model.vo.ViewActivityRemark;
import pers.johns.crm.query.ActivityRemarkQuery;
import pers.johns.crm.service.ActivityRemarkService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final UserMapper userMapper;

    @Override
    public PageInfo<Object> getActivityRemarksByPage(Integer id, Integer page) {
        PageHelper.startPage(page, Constants.DEFAULT_REMARK_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(activityRemarkMapper.selectByActivityId(new ActivityRemarkQuery(id)));

        List<Object> list = pageInfo.getList().stream()
                .map(this::convertToViewActivityRemark)
                .filter(remark -> !remark.getDeleted())
                .collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editActivityRemark(ViewActivityRemark viewActivityRemark) {
        ActivityRemark activityRemark = ActivityRemark
                .builder()
                .id(viewActivityRemark.getId())
                .noteContent(viewActivityRemark.getNoteContent())
                .editTime(LocalDateTime.now())
                .editBy(viewActivityRemark.getEditBy())
                .build();

        Integer count = activityRemarkMapper.updateActivityRemark(activityRemark);

        if (count != 1) throw new ActivityRemarkException("修改活动备注失败");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteActivityRemark(Integer id) {
        ActivityRemark activityRemark = ActivityRemark
                .builder()
                .id(id)
                .deleted(1)
                .build();

        Integer count = activityRemarkMapper.updateActivityRemark(activityRemark);

        if (count != 1) throw new ActivityRemarkException("删除活动备注失败");

        return true;
    }

    /**
     * 将一个 {@link ActivityRemark} 对象转换为 {@link ViewActivityRemark} 对象，用于前端展示
     * @param object {@link ActivityRemark} 对象
     * @return {@link ViewActivityRemark} 对象
     */
    private ViewActivityRemark convertToViewActivityRemark(Object object) {
        ActivityRemark activityRemark = (ActivityRemark) object;
        String creator = userMapper.selectNameById(activityRemark.getCreateBy());
        String editor = userMapper.selectNameById(activityRemark.getEditBy());

        return ViewActivityRemark
                .builder()
                .id(activityRemark.getId())
                .noteContent(activityRemark.getNoteContent())
                .creator(creator)
                .createTime(activityRemark.getCreateTime())
                .editor(editor)
                .editTime(activityRemark.getEditTime())
                .deleted(activityRemark.getDeleted() == 1)
                .build();
    }
}
