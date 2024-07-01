package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Permission;
import pers.johns.crm.model.po.Role;
import pers.johns.crm.model.po.User;
import pers.johns.crm.query.DataFilterQuery;

import java.util.List;

/**
 * ClassName    : UserMapperTest
 * <br/>
 * Description  : 测试 {@link UserMapper} 是否使用正常
 * <br/>
 * CreateTime   : 2024/6/24 16:45
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testGetUsers() {
        log.info("测试查询表 t_user ...");
        List<User> users = userMapper.selectAll(DataFilterQuery.builder().build());

        Assertions.assertNotNull(users);

        log.info("共查询出 {} 个结果", users.size());
        users.forEach(user -> log.info("用户信息: {}", user.toString()));
    }

    @Test
    public void testGetUser() {
        log.info("测试查询表 t_user ...");
        log.info("尝试查询用户 id 为 1 的用户信息");

        User user = userMapper.selectById(1);

        Assertions.assertNotNull(user);

        log.info("用户信息为: {}", user);
    }

    @Test
    public void testGetUserWithRolesAndPermissions() {
        log.info("测试查询表 t_user ...");
        log.info("尝试查询用户 id 为 2 的用户信息");

        User user = userMapper.selectById(2);

        Assertions.assertNotNull(user);

        List<Role> roles = user.getRoles();

        Assertions.assertNotNull(roles);

        List<Permission> permissions = roles
                .stream()
                .map(Role::getPermissions)
                .flatMap(List::stream).toList();

        Assertions.assertNotNull(permissions);

        log.info("用户 {} 角色与权限信息为: ", user.getName());
        log.info("  角色信息为: {} ", roles);
        log.info("  权限信息为: {} ", permissions);
    }
}
