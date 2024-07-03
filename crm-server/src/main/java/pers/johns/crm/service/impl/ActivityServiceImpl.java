package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ActivityException;
import pers.johns.crm.manager.RedisManager;
import pers.johns.crm.mapper.ActivityMapper;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.Activity;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.ActivitySearchQuery;
import pers.johns.crm.service.ActivityService;
import pers.johns.crm.utils.CacheUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName    : ActivityServiceImpl
 * <br/>
 * Description  : 活动业务实现类
 * <br/>
 * CreateTime   : 2024/7/1 21:37
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;
    private final UserMapper userMapper;
    private final RedisManager redisManager;

    @Override
    public PageInfo<Object> getActivitiesByPage(Integer currentPage) {
        PageHelper.startPage(currentPage, Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(activityMapper.selectAll(new ActivitySearchQuery()));

        List<Object> viewActivities = pageInfo.getList()
                .stream()
                .map(this::convertToViewActivity)
                .collect(Collectors.toList());

        pageInfo.setList(viewActivities);

        return pageInfo;
    }

    @Override
    public List<ViewActivity> getActivityOwners() {

        return CacheUtils.getCacheData(
                () -> redisManager.getList(Constants.ACTIVITY_OWNER_REDIS_KEY),
                () -> activityMapper.selectOwnerHavingActivity().stream()
                        .map(map -> ViewActivity.builder()
                                .ownerId((Integer) map.get("ownerId"))
                                .owner((String) map.get("name"))
                                .build())
                        .toList(),
                list -> redisManager.saveValue(Constants.ACTIVITY_OWNER_REDIS_KEY, list));
    }

    @Override
    public PageInfo<Object> searchActivitiesByPage(ActivitySearchQuery activitySearchQuery) {
        PageHelper.startPage(activitySearchQuery.getCurrent(), Constants.DEFAULT_PAGE_SIZE);
        PageInfo<Object> pageInfo = new PageInfo<>(activityMapper.selectAll(activitySearchQuery));

        List<Object> list = pageInfo.getList().stream()
                .map(this::convertToViewActivity)
                .collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    public Boolean addActivity(ViewActivity viewActivity) {
        Integer count = activityMapper.insertActivity(Activity
                .builder()
                .ownerId(viewActivity.getOwnerId())
                .name(viewActivity.getName())
                .cost(viewActivity.getCost())
                .startTime(viewActivity.getStartTime())
                .endTime(viewActivity.getEndTime())
                .description(viewActivity.getDescription())
                .createBy(viewActivity.getCreateBy())
                .createTime(LocalDateTime.now())
                .editBy(viewActivity.getEditBy())
                .editTime(LocalDateTime.now())
                .build());

        if (count != 1) throw new ActivityException("创建活动出错");

        CacheUtils.removeCacheData(redisManager::deleteValue, Constants.ACTIVITY_OWNER_REDIS_KEY);

        return true;
    }

    @Override
    public ViewActivity getActivity(Integer id) {
        Activity activity = activityMapper.selectById(id);
        return convertToViewActivity(activity);
    }

    @Override
    public Boolean editActivity(ViewActivity viewActivity) {
        Integer count = activityMapper.updateActivity(Activity
                .builder()
                .id(viewActivity.getId())
                .ownerId(viewActivity.getOwnerId())
                .name(viewActivity.getName())
                .cost(viewActivity.getCost())
                .startTime(viewActivity.getStartTime())
                .endTime(viewActivity.getEndTime())
                .description(viewActivity.getDescription())
                .editBy(viewActivity.getEditBy())
                .editTime(LocalDateTime.now())
                .build());

        if (count != 1) throw new ActivityException("修改活动出错");

        CacheUtils.removeCacheData(redisManager::deleteValue, Constants.ACTIVITY_OWNER_REDIS_KEY);

        return null;
    }

    /**
     * 将一个 {@link Activity} 对象转换为 {@link ViewActivity} 对象，用于前端展示
     * @param item {@link Activity} 对象
     * @return {@link ViewActivity} 对象
     */
    private ViewActivity convertToViewActivity(Object item) {
        Activity activity = (Activity) item;
        String owner = activity.getOwnerId() == null ? null : userMapper.selectNameById(activity.getOwnerId());
        return ViewActivity.builder()
                .id(activity.getId())
                .name(activity.getName())
                .ownerId(activity.getOwnerId())
                .owner(owner)
                .name(activity.getName())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .cost(activity.getCost())
                .createTime(activity.getCreateTime())
                .description(activity.getDescription())
                .build();
    }
}
