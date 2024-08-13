package com.javalab.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "login/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
}
