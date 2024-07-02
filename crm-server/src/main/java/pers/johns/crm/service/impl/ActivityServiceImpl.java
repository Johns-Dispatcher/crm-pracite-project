package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.manager.RedisManager;
import pers.johns.crm.mapper.ActivityMapper;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.Activity;
import pers.johns.crm.model.vo.ViewActivity;
import pers.johns.crm.query.DataFilterQuery;
import pers.johns.crm.service.ActivityService;
import pers.johns.crm.utils.CacheUtils;

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

        PageInfo<Object> pageInfo = new PageInfo<>(activityMapper.selectAll(DataFilterQuery.builder().build()));

        List<Object> viewActivities = pageInfo.getList().stream().map(a -> {
            Activity activity = (Activity) a;
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
                    .build();
        }).collect(Collectors.toList());

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
}
