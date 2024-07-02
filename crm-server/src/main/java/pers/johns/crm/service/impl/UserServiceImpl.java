package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.UserException;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.User;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.query.DataFilterQuery;
import pers.johns.crm.service.RedisService;
import pers.johns.crm.service.UserService;
import pers.johns.crm.utils.JsonUtils;
import pers.johns.crm.utils.JwtUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName    : UserServiceImpl
 * <br/>
 * Description  : 用户业务实现类
 * <br/>
 * CreateTime   : 2024/6/26 10:17
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginAct)
            throws UsernameNotFoundException {
        User user = userMapper.selectByLoginAct(loginAct);

        if (user == null) {
            throw new UsernameNotFoundException("登录账户不存在");
        }

        return new SecurityUser(user);
    }


    @Override
    public Map<String, Object> renewalToken(Authentication authentication) {
        Map<String, Object> data = new HashMap<>();

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        String username = securityUser.getUsername();

        log.info("为用户 {} 更新 Token ...", username);

        long expireTime = Constants.DEFAULT_NOT_REMEMBER_TIME;

        String json = JsonUtils.toJson(securityUser);
        String jwt = JwtUtils.createJWT(json, expireTime);

        redisService.removeValue(Constants.REDIS_JWT_KEY_PREFIX + username);
        redisService.setValue(Constants.REDIS_JWT_KEY_PREFIX + username, jwt, expireTime);

        data.put("token", jwt);
        data.put("expireTime", expireTime);

        return data;
    }

    @Override
    public PageInfo<Object> getUserByPage(Integer currentPage) {
        // 设置分页初始值
        PageHelper.startPage(currentPage, Constants.DEFAULT_PAGE_SIZE);
        // 查询 User 并进行分页，同时获取分页数据
        // 这里不能直接用 getAllUsers 直接获取包装完的视图用户集合，会导致无法正确获取分页信息
        PageInfo<Object> userPageInfo = new PageInfo<>(
                userMapper.selectAll(DataFilterQuery.builder().build())
        );
        // 对将查询出的 User 转换为视图用户对象
        List<Object> viewUsers = userPageInfo.getList().stream().map(user -> {
                User u = (User) user;
                return ViewUser.builder()
                        .id(u.getId())
                        .loginAct(u.getLoginAct())
                        .name(u.getName())
                        .phone(u.getPhone())
                        .email(u.getEmail())
                        .createTime(u.getCreateTime())
                        .build();
        }).collect(Collectors.toList());
        // 将视图用户对象列表放回分页信息对象中
        userPageInfo.setList(viewUsers);

        return userPageInfo;
    }

    @Override
    public ViewUser getUserByLoginAct(String loginAct) {
        User user = userMapper.selectByLoginAct(loginAct);
        if (user == null) return null;

        String creator = user.getCreateBy() == null ? null : userMapper.selectNameById(user.getCreateBy());
        String editor = user.getEditBy() == null ? null : userMapper.selectNameById(user.getEditBy());

        return ViewUser.builder()
                .id(user.getId())
                .name(user.getName())
                .loginAct(user.getLoginAct())
                .phone(user.getPhone())
                .email(user.getEmail())
                .accountEnabled(user.getAccountEnabled() == 1)
                .accountNoExpired(user.getAccountNoExpired() == 1)
                .accountNoLocked(user.getAccountNoLocked() == 1)
                .credentialsNoExpired(user.getCredentialsNoExpired() == 1)
                .createTime(user.getCreateTime())
                .creatorName(creator == null ? "系统内置" : creator)
                .editTime(user.getEditTime())
                .editorName(editor == null ? "系统修改" : editor)
                .lastLoginTime(user.getLastLoginTime())
                .build();
    }

    @Override
    public Boolean checkLoginAct(String loginAct) {
        return userMapper.checkLoginAct(loginAct) != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUser(ViewUser viewUser) {
        User user = User.builder()
                .loginAct(viewUser.getLoginAct())
                .loginPwd(passwordEncoder.encode(viewUser.getLoginPwd()))
                .phone(viewUser.getPhone())
                .email(viewUser.getEmail())
                .name(viewUser.getName())
                .accountEnabled(viewUser.getAccountEnabled() ? 1 : 0)
                .accountNoExpired(viewUser.getAccountNoExpired() ? 1 : 0)
                .accountNoLocked(viewUser.getAccountNoLocked() ? 1 : 0)
                .credentialsNoExpired(viewUser.getCredentialsNoExpired() ? 1 : 0)
                .createBy(viewUser.getCreateBy())
                .createTime(LocalDateTime.now())
                .editBy(viewUser.getEditBy())
                .editTime(LocalDateTime.now()).build();

        Integer count = userMapper.insertUser(user);

        if (count != 1) {
            throw new RuntimeException("添加用户异常");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editUser(ViewUser viewUser) {
        User user = User.builder()
                .id(viewUser.getId())
                .loginAct(viewUser.getLoginAct())
                .loginPwd(viewUser.getLoginPwd() == null ? null : passwordEncoder.encode(viewUser.getLoginPwd()))
                .phone(viewUser.getPhone())
                .email(viewUser.getEmail())
                .name(viewUser.getName())
                .accountEnabled(viewUser.getAccountEnabled() ? 1 : 0)
                .accountNoExpired(viewUser.getAccountNoExpired() ? 1 : 0)
                .accountNoLocked(viewUser.getAccountNoLocked() ? 1 : 0)
                .credentialsNoExpired(viewUser.getCredentialsNoExpired() ? 1 : 0)
                .editBy(viewUser.getEditBy())
                .editTime(LocalDateTime.now()).build();

        Integer count = userMapper.updateUser(user);
        if (count != 1) {
            throw new UserException("修改用户失败");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUser(Integer id) {
        Integer count = userMapper.deleteById(id);

        if (count != 1) throw new UserException("删除用户失败");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUsersByIds(List<Integer> ids) {
        if (ids.isEmpty()) throw new UserException("批量删除数量不正确");

        Integer count = userMapper.deleteUses(ids);

        if (count != ids.size()) throw new UserException("批量删除失败");

        return true;
    }
}
