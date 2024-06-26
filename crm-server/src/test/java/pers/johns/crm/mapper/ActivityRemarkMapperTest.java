package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.ActivityRemark;

import java.util.List;

/**
 * ClassName    : ActivityRemarkMapperTest
 * <br/>
 * Description  : 测试 {@link ActivityRemarkMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/24 23:30
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class ActivityRemarkMapperTest {
    @Resource
    ActivityRemarkMapper activityRemarkMapper;

    @Test
    public void testGetActivityRemarks() {
        log.info("测试查询表 t_activity_remark ... ");

        List<ActivityRemark> activityRemarks = activityRemarkMapper.selectAll();
        Assertions.assertNotNull(activityRemarks);

        log.info("共查询出 {} 个结果", activityRemarks.size());

        activityRemarks.forEach(activityRemark -> log.info("活动备注信息: {}", activityRemark));
    }
}
