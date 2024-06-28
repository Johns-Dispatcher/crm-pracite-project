package pers.johns.crm.service.impl;

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
import pers.johns.crm.service.RedisService;
import pers.johns.crm.service.UserService;
import pers.johns.crm.utils.JsonUtils;
import pers.johns.crm.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

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
}
