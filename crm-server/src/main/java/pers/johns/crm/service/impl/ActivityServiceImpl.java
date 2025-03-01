package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ActivityException;
import pers.johns.crm.exception.ActivityRemarkException;
import pers.johns.crm.manager.RedisManager;
import pers.johns.crm.mapper.ActivityMapper;
import pers.johns.crm.mapper.ActivityRemarkMapper;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.Activity;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.ActivityQuery;
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
    private final ActivityRemarkMapper activityRemarkMapper;
    private final UserMapper userMapper;
    private final RedisManager redisManager;

    @Override
    public PageInfo<Object> getActivitiesByPage(Integer currentPage) {
        PageHelper.startPage(currentPage, Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(activityMapper.selectAll(new ActivityQuery()));

        List<Object> viewActivities = pageInfo.getList()
                .stream()
                .map(this::convertToViewActivity)
                .collect(Collectors.toList());

        pageInfo.setList(viewActivities);

        return pageInfo;
    }

    @Override
    public PageInfo<Object> searchActivitiesByPage(ActivityQuery activitySearchQuery) {
        PageHelper.startPage(activitySearchQuery.getCurrent(), Constants.DEFAULT_PAGE_SIZE);
        PageInfo<Object> pageInfo = new PageInfo<>(activityMapper.selectAll(activitySearchQuery));

        List<Object> list = pageInfo.getList().stream()
                .map(this::convertToViewActivity)
                .collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    public List<ViewActivity> getAllActivitiesName() {
        return CacheUtils.getCacheData(
                () -> redisManager.getList(Constants.ACTIVITY_NAME_REDIS_KEY, ViewActivity.class),
                () -> activityMapper.selectAll(new ActivityQuery()).stream()
                        .map(activity -> ViewActivity.builder()
                                .id(activity.getId())
                                .name(activity.getName())
                                .build())
                        .toList(),
                list -> redisManager.saveValue(Constants.ACTIVITY_NAME_REDIS_KEY, list)
        );
    }

    @Override
    public List<ViewActivity> getOngoingActivities() {
        ActivityQuery activityQuery = new ActivityQuery();
        activityQuery.setNowTime(LocalDateTime.now());

        return CacheUtils.getCacheData(
                () -> redisManager.getList(Constants.ACTIVITY_ONGOING_NAME_REDIS_KEY, ViewActivity.class),
                () -> activityMapper.selectAll(activityQuery).stream()
                        .map(activity -> ViewActivity.builder()
                                .id(activity.getId())
                                .name(activity.getName())
                                .build())
                        .toList(),
                list -> redisManager.saveValue(Constants.ACTIVITY_ONGOING_NAME_REDIS_KEY, list)
        );
    }

    @Override
    public List<ViewActivity> getActivityOwners() {
        return CacheUtils.getCacheData(
                () -> redisManager.getList(Constants.ACTIVITY_OWNER_REDIS_KEY, ViewActivity.class),
                () -> activityMapper.selectOwnerHavingActivity().stream()
                        .map(map -> ViewActivity.builder()
                                .ownerId((Integer) map.get("ownerId"))
                                .owner((String) map.get("name"))
                                .build())
                        .toList(),
                list -> redisManager.saveValue(Constants.ACTIVITY_OWNER_REDIS_KEY, list));
    }

    @Override
    public ViewActivity getActivity(Integer id) {
        Activity activity = activityMapper.selectById(id);
        return convertToViewActivity(activity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

        clearActivityCache();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

        clearActivityCache();

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteActivity(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);

        Integer remarkCount = activityRemarkMapper.countActivitiesRemarks(ids);

        Integer deleteRemarkCount = activityRemarkMapper.deleteActivityRemarkByActivityId(id);

        if (!Objects.equals(remarkCount, deleteRemarkCount)) throw new ActivityRemarkException("删除活动备注失败");

        Integer count = activityMapper.deleteActivityById(id);

        if (count != 1) throw new ActivityException("删除活动失败");

        clearActivityCache();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteBulkActivity(List<Integer> ids) {
        Integer remarkCount = activityRemarkMapper.countActivitiesRemarks(ids);

        Integer deleteRemarkCount = activityRemarkMapper.deleteBulkRemarks(ids);

        if (!Objects.equals(remarkCount, deleteRemarkCount)) throw new ActivityRemarkException("批量删除活动备注失败");

        Integer count = activityMapper.deleteBulkActivities(ids);

        if (count != ids.size()) throw new ActivityException("批量删除活动失败");

        clearActivityCache();

        return true;
    }

    /**
     * 将一个 {@link Activity} 对象转换为 {@link ViewActivity} 对象，用于前端展示
     * @param item {@link Activity} 对象
     * @return {@link ViewActivity} 对象
     */
    private ViewActivity convertToViewActivity(Object item) {
        Activity activity = (Activity) item;
        String owner = activity.getOwnerId() == null ? null : userMapper.selectNameById(activity.getOwnerId());
        String creator = activity.getCreateBy() == null ? null : userMapper.selectNameById(activity.getCreateBy());
        String editor = activity.getEditBy() == null ? null : userMapper.selectNameById(activity.getEditBy());
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
                .creator(creator)
                .editTime(activity.getEditTime())
                .editor(editor)
                .description(activity.getDescription())
                .build();
    }

    /**
     * 用于清除相关缓存，以便数据更新后重新进行缓存
     */
    private void clearActivityCache() {
        CacheUtils.removeCacheData(redisManager::batchDeleteValues, Constants.ACTIVITY_PREFIX_REDIS_KEY);
    }
}
