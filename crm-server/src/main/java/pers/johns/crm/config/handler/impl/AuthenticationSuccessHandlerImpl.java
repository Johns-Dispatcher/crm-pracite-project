package pers.johns.crm.config.handler.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.utils.HttpResponseUtils;
import pers.johns.crm.utils.JsonUtils;

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
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("用户登录成功: {}", authentication);

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        HttpResult ok = HttpResult.OK();
        ok.setData(securityUser);

        String okJson = JsonUtils.toJson(ok);

        HttpResponseUtils.responseJson(response, okJson);
    }
}
