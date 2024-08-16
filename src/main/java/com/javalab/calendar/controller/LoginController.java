package com.javalab.calendar.controller;

import com.javalab.calendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/login")
@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    // 로그인 화면
    @GetMapping(value = "/login.do")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception){
        log.info("MemberController loginMember 메소드");

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "login/login";
    }

}
