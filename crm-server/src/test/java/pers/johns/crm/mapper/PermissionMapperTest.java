package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Permission;

import java.util.List;

/**
 * ClassName    : PermissionMapperTest
 * <br/>
 * Description  : 测试 {@link PermissionMapper} 使用是否正常
 * <br/>
 * CreateTime   : 2024/6/24 21:01
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class PermissionMapperTest {
    @Resource
    private PermissionMapper permissionMapper;

    @Test
    public void testGetPermissions() {
        log.info("测试查询表 t_permission ...");
        List<Permission> permissions = permissionMapper.selectAll();

        Assertions.assertNotNull(permissions);

        log.info("共查询出 {} 个结果", permissions.size());

        permissions.forEach(permission -> log.info("权限信息: {}", permission));
    }

    @Test
    public void testGetPermission() {
        log.info("测试查询表 t_permission ...");
        log.info("尝试查询 id 为 1 的权限信息");
        Permission permission = permissionMapper.selectById(1);

        Assertions.assertNotNull(permission);

        log.info("权限信息: {}", permission);
    }
}
