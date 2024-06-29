package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.model.po.User;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.service.RedisService;
import pers.johns.crm.service.UserService;
import pers.johns.crm.utils.JsonUtils;
import pers.johns.crm.utils.JwtUtils;

import java.util.Collections;
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
    public List<ViewUser> getAllUsers() {
        // 暂时没用上捏~
        List<User> users = userMapper.selectAll();
        return users.stream().map(ViewUser::new).toList();
    }

    @Override
    public PageInfo<Object> getUserByPage(Integer currentPage) {
        // 设置分页初始值
        PageHelper.startPage(currentPage, Constants.DEFAULT_PAGE_SIZE);
        // 查询 User 并进行分页，同时获取分页数据
        // 这里不能直接用 getAllUsers 直接获取包装完的视图用户集合，会导致无法正确获取分页信息
        PageInfo<Object> userPageInfo = new PageInfo<>(userMapper.selectAll());
        // 对将查询出的 User 转换为视图用户对象
        List<Object> viewUsers = userPageInfo.getList().stream().map(ViewUser::new).collect(Collectors.toList());
        // 将视图用户对象列表放回分页信息对象中
        userPageInfo.setList(viewUsers);

        return userPageInfo;
    }

    @Override
    public ViewUser getUserByLoginAct(String loginAct) {
        User user = userMapper.selectByLoginAct(loginAct);
        if (user == null) return null;

        ViewUser viewUser = new ViewUser(user);

        User creator = userMapper.selectById(viewUser.getCreateBy());
        User editor = userMapper.selectById(viewUser.getEditBy());

        viewUser.setCreatorName(creator == null ? "系统内置" : creator.getName());
        viewUser.setEditorName(editor == null ? "系统修改" : editor.getName());

        return viewUser;
    }
}
