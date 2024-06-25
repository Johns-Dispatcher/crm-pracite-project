package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.mode.po.Permission;
import pers.johns.crm.mode.po.Role;

import java.util.List;

/**
 * ClassName    : RoleMapperTest
 * <br/>
 * Description  : 测试 {@link RoleMapper} 使用是否正常
 * <br/>
 * CreateTime   : 2024/6/24 20:04
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class RoleMapperTest {
    @Resource
    private RoleMapper roleMapper;

    @Test
    public void testGetRoles() {
        log.info("测试查询表 t_role ...");
        List<Role> roles = roleMapper.selectAll();

        Assertions.assertNotNull(roles);

        log.info("共查询出 {} 个结果", roles.size());
        roles.forEach(role -> log.info("角色信息: {}", role));
    }

    @Test
    public void testGetRole() {
        log.info("测试查询表 t_role ...");
        log.info("尝试查询角色 id 为 1 的角色信息");

        Role role = roleMapper.selectById(1);

        Assertions.assertNotNull(role);

        log.info("角色信息为: {}", role);
    }

    @Test
    public void testGetRoleWithPermission() {
        log.info("测试查询表 t_role ...");
        log.info("尝试查询角色 id 为 2 的角色信息");

        Role role = roleMapper.selectById(2);

        Assertions.assertNotNull(role);

        List<Permission> permissions = role.getPermissions();

        Assertions.assertNotNull(permissions);

        log.info("角色 {} 的权限信息为: {}", role.getRoleName(), permissions);
    }
}
