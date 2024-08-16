package com.javalab.calendar.security.handler;

import com.javalab.calendar.dto.CustomUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        log.info("----------------------------------------------------------");
        log.info("CustomSocialLoginSuccessHandler onAuthenticationSuccess...");
        log.info(authentication.getPrincipal());

        // CustomUser로 캐스팅
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        // 로그 출력
        log.info("customUser.getUsername(): " + customUser.getUsername());

        // 비밀번호가 "1111"인지 확인 (소셜 로그인 여부를 간접적으로 확인)
        boolean isDefaultPassword = passwordEncoder.matches("1111", customUser.getPassword());
        log.info("Password matches '1111': " + isDefaultPassword);

        // 비밀번호가 "1111"이라면, 사용자 정보를 수정하는 페이지로 리다이렉트
        if (isDefaultPassword) {
            log.info("Redirecting to member modification page...");
            response.sendRedirect("/member/modify.do");
        } else {
            // 그렇지 않다면 기본 페이지로 리다이렉트
            log.info("Redirecting to home page...");
            response.sendRedirect("/");
        }
    }
}
