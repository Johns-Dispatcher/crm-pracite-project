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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource)
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

        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                .configurationSource(configurationSource)
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
