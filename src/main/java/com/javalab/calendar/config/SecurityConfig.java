package com.javalab.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**").permitAll()  // 인증 없이 접근 허용
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // 커스텀 로그인 페이지 설정
                        .permitAll()  // 로그인 페이지는 인증 없이 접근 허용
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }
}
