package pers.johns.crm.config;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity.formLogin(formLoginConfigurer -> formLoginConfigurer
                // 设置登录地址
                .loginProcessingUrl("/api/login")
                // 设置用户参数名
                .usernameParameter("loginAct")
                // 设置密码参数名
                .passwordParameter("loginPwd")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
        );

        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login").permitAll()
                // 任何请求需要认证
                .anyRequest().authenticated()
        );

        // 关闭跨域保护
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
