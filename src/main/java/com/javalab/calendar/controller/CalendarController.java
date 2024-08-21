//package com.javalab.calendar.controller;
//
//import com.javalab.calendar.dto.CalendarDto;
//import com.javalab.calendar.service.CalendarService;
//import com.javalab.calendar.vo.CalendarVo;
//import com.javalab.calendar.vo.MemberVo;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//
//@Controller
//@RequestMapping("/calendar")
//@Slf4j
//public class CalendarController {
//
//    @Autowired
//    private CalendarService calendarService;
//
//    /**
//     * 캘린더 보기
//     * 현재 로그인한 사용자의 캘린더 정보를 가져옴
//     */
//    @GetMapping("/view.do")
//    public String viewCalendar(@AuthenticationPrincipal MemberVo memberVo, Model model) {
//        CalendarDto calendar = calendarService.getCalendarByUserId(memberVo.getMemberId());
//        model.addAttribute("calendar", calendar);
//        return "calendar/maincalendar"; // 캘린더 상세 페이지로 이동
//    }
//
//    /**
//     * 캘린더 수정 폼 보기
//     */
//    @GetMapping("/edit.do")
//    public String editCalendarForm(@AuthenticationPrincipal MemberVo memberVo, Model model) {
//        CalendarDto calendar = calendarService.getCalendarByUserId(memberVo.getMemberId());
//        model.addAttribute("calendar", calendar);
//        return "calendar/calendarEdit"; // 캘린더 수정 폼으로 이동
//    }
//
//    /**
//     * 캘린더 수정 처리
//     */
//    @PostMapping("/edit.do")
//    public String updateCalendar(@ModelAttribute("calendar") @Valid CalendarVo calendarVo,
//                                 @AuthenticationPrincipal MemberVo memberVo) {
//        // 로그인된 사용자의 캘린더 업데이트
//        calendarVo.setMember_id(memberVo.getMemberId());
//        calendarVo.setCalen_update(new Date()); // 수정된 날짜 갱신
//        calendarService.updateEvent(calendarVo);
//        return "redirect:/calendar/view.do"; // 수정 후 다시 캘린더 보기 페이지로 리다이렉트
//    }
//
//    /**
//     * 일정 등록 폼 보기
//     */
//    @GetMapping("/event/create.do")
//    public String createEventForm(Model model) {
//        model.addAttribute("eventVo", new CalendarVo());
//        return "calendar/eventCreate"; // 일정 등록 폼 페이지로 이동
//    }
//
//
//}
