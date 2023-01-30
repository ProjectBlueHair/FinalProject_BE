package com.bluehair.hanghaefinalproject.config;

import com.bluehair.hanghaefinalproject.security.exception.CustomAccessDeniedHandler;
import com.bluehair.hanghaefinalproject.security.exception.CustomAuthenticationEntryPoint;
import com.bluehair.hanghaefinalproject.security.exception.CustomJwtExceptionHandlerFilter;
import com.bluehair.hanghaefinalproject.security.jwt.JwtAuthFilter;
import com.bluehair.hanghaefinalproject.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
//@EnableWebSecurity(debug = true) // Filter 적용 순서 등 확인할 때 쓰시면 좋습니다. 서비스 런칭하고는 옵션 삭제
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console());
//    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(corsConfigurationSource());

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .antMatchers("/api/member/login", "/api/member/signup", "/api/member/reissuance").permitAll()
                .antMatchers("/api/member/validate/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/member/mypage/**").permitAll()
                .antMatchers("/api/subscribe/*").permitAll()
                .antMatchers("/chat/*").permitAll()
                .antMatchers("/topic/*").permitAll()
                .antMatchers("/app/*").permitAll()
                .antMatchers().permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomJwtExceptionHandlerFilter(), DisableEncodeUrlFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://152.69.229.168:84/");
        configuration.addAllowedOrigin("http://d6y8g7dkunqrb.cloudfront.net/");
        configuration.addAllowedOrigin("https://d6y8g7dkunqrb.cloudfront.net/");
        configuration.addAllowedOrigin("http://oncounter.co.kr/");
        configuration.addAllowedOrigin("https://oncounter.co.kr/");

        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE", "PUT", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.addExposedHeader("AccessToken");
        configuration.addExposedHeader("RefreshToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
