package pers.johns.crm.config;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.filter.JwtVerifyFilter;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName    : SecurityConfig
 * <br/>
 * Description  : 系统安全设置
 * <br/>
 * CreateTime   : 2024/6/26 10:48
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final JwtVerifyFilter jwtVerifyFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource)
            throws Exception {

        httpSecurity.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.formLogin(formLoginConfigurer -> formLoginConfigurer
                // 设置登录地址
                .loginProcessingUrl(Constants.LOGIN_URI)
                // 设置用户参数名
                .usernameParameter(Constants.LOGIN_USERNAME_PARAMETER)
                // 设置密码参数名
                .passwordParameter(Constants.LOGIN_PASSWORD_PARAMETER)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
        );

        httpSecurity.logout(logout -> logout
                .logoutUrl(Constants.LOGOUT_URI)
                .logoutSuccessHandler(logoutSuccessHandler)
        );

        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(Constants.LOGIN_URI).permitAll()
                // 任何请求需要认证
                .anyRequest().authenticated()
        );

        // 关闭跨域保护
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // 配置同源策略
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                .configurationSource(configurationSource)
        );

        // 关闭 Session 机制
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return httpSecurity.build();
    }

    /**
     * 配置密码编码器
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置同源策略，解决 AJAX 跨域问题
     * @return {@link CorsConfigurationSource} 对象
     */
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 任何源头都可以访问
        corsConfiguration.setAllowedOrigins(List.of("*"));
        // 任何方法都可以访问
        corsConfiguration.setAllowedMethods(List.of("*"));
        // 任何请求头都可以访问
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 配置任意请求路径都可以访问
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
