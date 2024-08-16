package com.javalab.calendar.security;

import com.javalab.calendar.dto.CustomUser;
import com.javalab.calendar.service.MemberService;
import com.javalab.calendar.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest: {}", userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("clientName: {}", clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;
        String name = null;

        if ("kakao".equals(clientName)) {
            email = getKakaoEmail(paramMap);
            name = getKakaoNickname(paramMap);
        }

        log.info("=================================");
        log.info("카카오에서 받아온 이메일: {}", email);
        log.info("카카오에서 받아온 닉네임: {}", name);
        log.info("=================================");

        // 이메일을 통해 기존 회원 여부 확인
        MemberVo memberVo = memberService.findMemberByEmail(email);

        if (memberVo == null) {
            // 기존 회원이 없는 경우, 새 회원 생성
            memberVo = createNewMember(email, name);
            memberService.saveMember(memberVo);
        }

        return new CustomUser(memberVo, paramMap);
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO-----------------------------------------");
        LinkedHashMap accountMap = (LinkedHashMap) paramMap.get("kakao_account");
        String email = (String) accountMap.get("email");
        log.info("email: {}", email);
        return email;
    }

    private String getKakaoNickname(Map<String, Object> paramMap) {
        LinkedHashMap propertiesMap = (LinkedHashMap) paramMap.get("properties");
        return (String) propertiesMap.get("nickname");
    }

    private MemberVo createNewMember(String email, String name) {
        MemberVo memberVo = new MemberVo();
        memberVo.setEmail(email);
        memberVo.setName(name);
        memberVo.setPassword(passwordEncoder.encode("default_password")); // 임시 비밀번호 설정

        // member_id와 같은 다른 필드가 필요한 경우 초기화
        // memberVo.setMember_Id(...);

        return memberVo;
    }
}
