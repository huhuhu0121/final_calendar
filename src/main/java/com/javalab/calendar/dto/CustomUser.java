package com.javalab.calendar.dto;

import com.javalab.calendar.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Map;

@Slf4j
public class CustomUser extends User implements OAuth2User, Serializable {

    private static final long serialVersionUID = 1L;

    private MemberVo memberVo;  // 일반 시큐리티 로그인
    private Map<String, Object> attributes; // 소셜 로그인 정보

    // 일반 시큐리티 로그인 사용자 생성자
    public CustomUser(MemberVo memberVo) {
        // 부모 클래스(User) 생성자를 호출
        super(String.valueOf(memberVo.getMember_Id()), memberVo.getPassword(), null); // 권한 정보 없이 설정

        log.info("CustomUser 생성자 호출 - 일반 로그인");

        this.memberVo = memberVo;
    }

    // 소셜 로그인 사용자용 생성자, 파라미터로 attributes 추가됨
    public CustomUser(MemberVo memberVo, Map<String, Object> attributes) {
        // 부모 클래스(User) 생성자를 호출
        super(String.valueOf(memberVo.getMember_Id()), memberVo.getPassword(), null); // 권한 정보 없이 설정

        log.info("CustomUser 생성자 호출 - 소셜 로그인");

        this.memberVo = memberVo;
        this.attributes = attributes;
    }

    @Override
    public String getUsername() {
        // member_id는 int이므로 String으로 변환하여 반환
        return String.valueOf(this.memberVo.getMember_Id());
    }

    @Override
    public String getPassword() {
        return this.memberVo.getPassword();
    }

    public String getName() {
        return this.memberVo.getName();
    }

    public String getEmail() {
        return this.memberVo.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
