package pers.johns.crm.config.handler.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
 * ClassName    : LogoutSuccessHandlerImpl
 * <br/>
 * Description  : 退出登录处理器
 * <br/>
 * CreateTime   : 2024/6/28 0:25
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        log.info("用户 {} 退出成功，清除 Redis 中 Token 数据...", securityUser.getUsername());

        Boolean success = redisService.removeValue(Constants.REDIS_JWT_KEY_PREFIX + securityUser.getUsername());

        log.info("Redis 清除是否成功: {}", success);


        HttpResult result = HttpResult.OK("退出成功");

        HttpResponseUtils.responseJson(response, JsonUtils.toJson(result));
    }
}
