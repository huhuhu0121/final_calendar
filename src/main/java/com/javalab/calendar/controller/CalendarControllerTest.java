package com.javalab.calendar.controller;

import com.javalab.calendar.vo.CalendarVo;
import com.javalab.calendar.vo.MemberVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@Slf4j
public class CalendarControllerTest {

    @GetMapping("/maincalendar")
    public String mainCalendar() {
        return "calendar/maincalendar"; // HTML 파일 이름
    }

}
