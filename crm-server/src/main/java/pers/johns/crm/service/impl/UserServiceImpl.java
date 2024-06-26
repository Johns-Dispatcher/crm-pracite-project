package pers.johns.crm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.johns.crm.mapper.UserMapper;
import pers.johns.crm.mode.po.User;
import pers.johns.crm.mode.vo.SecurityUser;
import pers.johns.crm.service.UserService;

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
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String loginAct)
            throws UsernameNotFoundException {
        User user = userMapper.selectByLoginAct(loginAct);

        if (user == null) {
            throw new UsernameNotFoundException("登录账户不存在");
        }

        return new SecurityUser(user);
    }
}
