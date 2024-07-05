package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Activity;
import pers.johns.crm.model.po.ActivityRemark;
import pers.johns.crm.query.ActivityQuery;

import java.util.List;
import java.util.Map;

/**
 * ClassName    : ActivityMapperTest
 * <br/>
 * Description  : 测试 {@link ActivityMapper} 使用是否正常
 * <br/>
 * CreateTime   : 2024/6/24 22:56
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class ActivityMapperTest {
    @Resource
    private ActivityMapper activityMapper;

    @Test
    public void testGetActivities() {
        log.info("测试查询表 t_activity ...");
        List<Activity> activities = activityMapper.selectAll(new ActivityQuery());

        Assertions.assertNotNull(activities);

        log.info("总共查询到 {} 个结果", activities.size());

        activities.forEach(activity -> log.info("活动信息: {}", activity));
    }

    @Test
    public void testGetActivityWithRemarks() {
        log.info("测试查询表 t_activity ...");
        log.info("尝试获取 id 为 2 活动信息");
        Activity activity = activityMapper.selectById(2);

        Assertions.assertNotNull(activity);

        List<ActivityRemark> activityRemarks = activity.getActivityRemarks();

        Assertions.assertNotNull(activityRemarks);

        log.info("活动 {} 的活动备注信息为: {}", activity.getName(), activityRemarks);
    }

    @Test
    public void testGetOwnerHavingActivity() {
        List<Map<String, Object>> maps = activityMapper.selectOwnerHavingActivity();
        log.info(maps.toString());
    }
}
