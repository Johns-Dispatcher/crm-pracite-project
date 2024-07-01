package pers.johns.crm.config.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.service.RedisService;
import pers.johns.crm.utils.HttpResponseUtils;
import pers.johns.crm.utils.JsonUtils;
import pers.johns.crm.utils.JwtUtils;

import java.io.IOException;

/**
 * ClassName    : AuthenticationSuccessHandlerImpl
 * <br/>
 * Description  : 登录成功处理器
 * <br/>
 * CreateTime   : 2024/6/26 10:54
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        // 获取认证后的用户信息
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String username = securityUser.getUsername();
        log.info("用户登录成功: {}", username);

        // 是否勾选记住登录
        boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));

        // 确认过期时间
        long expireTime = rememberMe ? Constants.DEFAULT_REMEMBER_TIME : Constants.DEFAULT_NOT_REMEMBER_TIME;
        log.info("JWT 过期时间为 {} ms", expireTime);
        // 为用户添加过期时间属性，用于前端监控是否要进行续期
        securityUser.setExpireTime(expireTime);

        // 设置 JWT 以及 Redis
        String jwt = JwtUtils.createJWT(JsonUtils.toJson(securityUser), expireTime);
        redisService.setValue(Constants.REDIS_JWT_KEY_PREFIX + username, jwt, expireTime);
        log.info("为用户 {} 生成 JWT，并存入 Redis: {}", username, jwt);

        // 响应前端
        HttpResult ok = HttpResult.OK();
        ok.setData(jwt);

        String okJson = JsonUtils.toJson(ok);

        HttpResponseUtils.responseJson(response, okJson);
    }
}
