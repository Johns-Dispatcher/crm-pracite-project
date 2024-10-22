package pers.johns.crm.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.HttpResultCode;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.service.RedisService;
import pers.johns.crm.utils.HttpResponseUtils;
import pers.johns.crm.utils.JsonUtils;
import pers.johns.crm.utils.JwtUtils;

import java.io.IOException;

/**
 * ClassName    : JwtVerifyFilter
 * <br/>
 * Description  : 对前端 Token 进行验证
 * <br/>
 * CreateTime   : 2024/6/27 20:36
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtVerifyFilter extends OncePerRequestFilter {

    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals(Constants.LOGIN_URI)) {
            filterChain.doFilter(request, response);
        } else {
            log.info("正在验证用户提交的 JWT");
            String jwt = request.getHeader(Constants.AUTHORIZATION_PARAMETER_NAME)
                    .trim().replace(Constants.AUTHORIZATION_TOKEN_PREFIX, "").trim();

            if (!StringUtils.hasText(jwt)) {
                // 用户未提交 Token
                HttpResult result = HttpResult.CustomResult(HttpResultCode.TOKEN_IS_EMPTY);
                HttpResponseUtils.responseJson(response, JsonUtils.toJson(result));
                log.info("验证失败 - 用户未提交 Token");
                return;
            }

            if (!JwtUtils.verifyJWT(jwt)) {
                // Token 校验失败
                HttpResult result = HttpResult.CustomResult(HttpResultCode.TOKEN_VERIFY_FAILED);
                HttpResponseUtils.responseJson(response, JsonUtils.toJson(result));
                log.info("验证失败 - Token 不合法或者已过期");
                return;
            }

            String jsonData = JwtUtils.parsePayloadData(jwt);

            SecurityUser securityUser = JsonUtils.toBean(jsonData, SecurityUser.class);

            String username = securityUser.getUsername();
            String redisJwt = (String) redisService.getValue(Constants.REDIS_JWT_KEY_PREFIX + username);

            if (!StringUtils.hasText(redisJwt)) {
                // 未能在 Redis 中获取 Token，Token 已经超时或者用户主动下线
                HttpResult result = HttpResult.CustomResult(HttpResultCode.TOKEN_IS_EXPIRED);
                HttpResponseUtils.responseJson(response, JsonUtils.toJson(result));
                log.info("验证失败 - 未能在 Redis 中获取 Token，Token 已经超时或者用户主动下线");
                return;
            }

            // 从 Redis 中更新续期时间
            Long expireTime = redisService.getExpireTime(Constants.REDIS_JWT_KEY_PREFIX + username);
            securityUser.setExpireTime(expireTime);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            log.info("验证成功 - 将用户信息注册到安全上下文当中");

            filterChain.doFilter(request, response);
        }
    }
}
