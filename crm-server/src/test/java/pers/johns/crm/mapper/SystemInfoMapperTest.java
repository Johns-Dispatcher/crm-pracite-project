package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.mode.po.SystemInfo;

import java.util.List;

/**
 * ClassName    : SystemInfoMapperTest
 * <br/>
 * Description  : 测试 {@link SystemInfoMapper} 使用是否正常
 * <br/>
 * CreateTime   : 2024/6/24 16:57
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class SystemInfoMapperTest {

    @Resource
    private SystemInfoMapper systemInfoMapper;

    @Test
    public void testGetSystemInfos() {
        log.info("测试查询表 t_system_info ...");
        List<SystemInfo> systemInfos = systemInfoMapper.selectAll();

        Assertions.assertNotNull(systemInfos);

        log.info("共查询出 {} 个结果", systemInfos.size());
        systemInfos.forEach(systemInfo -> log.info("系统信息: {}", systemInfo.toString()));
    }

    @Test
    public void testGetSystemInfo() {
        log.info("测试查询表 t_system_info ...");
        log.info("尝试查询用户 id 为 1 的系统信息");
        SystemInfo systemInfo = systemInfoMapper.selectById(1);

        Assertions.assertNotNull(systemInfo);

        log.info("系统信息为: {}", systemInfo);
    }
}