package com.javalab.calendar.config;

import com.javalab.calendar.security.handler.AuthFailureHandler;
import com.javalab.calendar.security.handler.AuthSucessHandler;
import com.javalab.calendar.security.CustomOAuth2UserService;
import com.javalab.calendar.security.handler.CustomAccessDeniedHandler;
import com.javalab.calendar.security.handler.CustomSocialLoginSuccessHandler;
import com.javalab.calendar.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomUserDetailsService customMemberService;	// 사용자 정보를 가져오는 인터페이스로 실질적인 로그인 처리를 담당하는 클래스
    private final AuthSucessHandler authSucessHandler;	// 로그인 성공 후처리를 담당하는 클래스
    private final AuthFailureHandler authFailureHandler;	// 로그인 실패 후처리를 담당하는 클래스

    @Bean
    public PasswordEncoder passwordEncoder() {	// 비밀번호 암호화를 위한 빈
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {	// 권한이 없는 페이지에 접근시 처리를 담당하는 빈
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {	// 소셜 로그인 성공 후처리를 담당하는 빈
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    /**
     * [securityFilterChain 메소드]
     * - HttpSecurity 빈 등록
     * - HttpSecurity 객체를 이용하여 보안 설정
     * @param http : HttpSecurity 객체
     * @param customOAuth2UserService : CustomOAuth2UserService 객체
     * @return SecurityFilterChain : SecurityFilterChain 객체
     * @throws Exception : 예외처리
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {

        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customMemberService).passwordEncoder(passwordEncoder());

        http
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/login.do")  // 로그인 페이지 설정
                        .loginProcessingUrl("/member/action.do")  // 로그인 처리 URL
                        .successHandler(authSucessHandler)  // 로그인 성공 핸들러
                        .failureHandler(authFailureHandler)  // 로그인 실패 핸들러
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout.do"))
                        .logoutSuccessUrl("/member/login.do")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/member/login.do","/member/logout.do", "/member/action", "/member/join.do", "/member/modify.do").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/ckeditor2/**", "/vendor/**", "/assets/**").permitAll()
                        .requestMatchers("/member/login.do","/member/logout.do", "/member/action", "/member/join.do/**", "/member/modify.do").permitAll()
                        .requestMatchers( "/event/detail.do/*").hasRole("USER")
                        .requestMatchers("/event/list.do/**", "/event/create.do/**", "/event/update.do/**", "/event/delete").hasRole("USER")
                        .requestMatchers( "/calendar/detail.do/*").hasRole("USER")
                        .requestMatchers("/calendar/list.do/**", "/calendar/create.do/**", "/calendar/update.do/**", "/calendar/delete").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/track/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/member/login.do?error=true&exception=Have been attempted to login from a new place. or session expired")
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler())
                )
                //.csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login.do")
                        .successHandler(authenticationSuccessHandler())
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );

        http.authenticationManager(auth.build());	// 인증 매니저 설정

        return http.build();	// 설정한 HttpSecurity 객체 반환
    }


}
