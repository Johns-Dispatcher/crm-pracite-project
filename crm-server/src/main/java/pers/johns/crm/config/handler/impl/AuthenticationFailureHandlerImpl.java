package pers.johns.crm.config.handler.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.utils.HttpResponseUtils;
import pers.johns.crm.utils.JsonUtils;

import java.io.IOException;

/**
 * ClassName    : AuthenticationFailureHandlerImpl
 * <br/>
 * Description  : 认证失败处理器
 * <br/>
 * CreateTime   : 2024/6/26 11:13
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@Component
@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        log.info("用户登录失败: {}", exception.getMessage());

        HttpResult fail = HttpResult.Fail(exception.getMessage());

        String failJson = JsonUtils.toJson(fail);

        HttpResponseUtils.responseJson(response, failJson);

    }
}
