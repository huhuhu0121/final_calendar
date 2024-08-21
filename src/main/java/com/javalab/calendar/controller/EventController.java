package com.javalab.calendar.controller;

import com.javalab.calendar.service.EventService;
import com.javalab.calendar.vo.EventVo;
import com.javalab.calendar.vo.MemberVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * 일정 내용 보기
     */
    @GetMapping("/{event_id}")
    public String detail(@PathVariable("event_id") int event_id, Model model) {
        log.info("Fetching details for event ID: {}", event_id);
        EventVo eventVo = eventService.getEvent(event_id);
        if (eventVo != null) {
            model.addAttribute("eventVo", eventVo);
            log.info("Event details retrieved: {}", eventVo);
        } else {
            log.warn("Event with ID: {} not found, redirecting to events list.", event_id);
            return "redirect:/events";
        }
        return "event/eventDetail";
    }

    /**
     * 일정 목록 보기
     */
    @GetMapping
    public String list(Model model) {
        log.info("Fetching event list.");
        List<EventVo> eventList = eventService.listEvent();
        model.addAttribute("eventList", eventList);
        log.info("Event list retrieved: {}", eventList);
        return "event/eventList";
    }

    /**
     * 일정 등록 폼 보기
     */
    @GetMapping("/create")
    public String create(Model model) {
        log.info("Displaying event creation form.");
        model.addAttribute("eventVo", new EventVo());
        return "event/eventCreate";
    }

    /**
     * 일정 등록 처리
     */
    @PostMapping("/create")
    public String createEvent(@ModelAttribute("eventVo") @Valid EventVo eventVo,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal MemberVo memberVo) {

        if (bindingResult.hasErrors()) {
            log.warn("Event creation failed due to validation errors: {}", bindingResult.getAllErrors());
            return "event/eventCreate";
        }

        // 세션에서 조회한 사용자를 작성자로 설정
        eventVo.setMemberId(memberVo.getMemberId());
        log.info("Creating event with details: {}", eventVo);
        eventService.createEvent(eventVo);
        log.info("Event created successfully with ID: {}", eventVo.getEventId());
        return "redirect:/events";
    }

    /**
     * 일정 수정 폼 보기
     */
    @GetMapping("/update")
    public String updateForm(@RequestParam("event_id") int event_id, Model model) {
        log.info("Fetching event for update with ID: {}", event_id);
        EventVo eventVo = eventService.getEvent(event_id);
        model.addAttribute("eventVo", eventVo);
        log.info("Event details retrieved for update: {}", eventVo);
        return "event/eventUpdate";
    }

    /**
     * 일정 수정 처리
     */
    @PostMapping("/update")
    public String update(@ModelAttribute("eventVo") @Valid EventVo eventVo,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal MemberVo memberVo) {

        if (bindingResult.hasErrors()) {
            log.warn("Event update failed due to validation errors: {}", bindingResult.getAllErrors());
            return "event/eventUpdate";
        }

        // 세션에서 조회한 사용자를 작성자로 설정
        eventVo.setMemberId(memberVo.getMemberId());
        log.info("Updating event with details: {}", eventVo);
        eventService.updateEvent(eventVo);
        log.info("Event updated successfully with ID: {}", eventVo.getEventId());
        return "redirect:/events";
    }

    /**
     * 일정 삭제 처리
     */
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("event_id") int event_id) {
        log.info("Deleting event with ID: {}", event_id);
        eventService.deleteEvent(event_id);
        log.info("Event deleted successfully with ID: {}", event_id);
        return "redirect:/events";
    }

    /**
     * AJAX 요청을 처리하는 일정 등록
     */
    @PostMapping("/create-ajax")
    @ResponseBody
    public ResponseEntity<String> createEventAjax(@RequestParam String title,
                                                  @RequestParam String startDate,
                                                  @RequestParam String endDate,
                                                  @RequestParam String content,
                                                  @RequestParam String location,
                                                  @AuthenticationPrincipal MemberVo memberVo) {

        // EventVo 객체 생성 및 설정
        EventVo eventVo = EventVo.builder()
                .title(title)
                .startDate(Date.valueOf(startDate))
                .endDate(Date.valueOf(endDate))
                .memo(content)
                .location(location)
                .allDay(1) // 필요한 경우 적절히 설정
                .publice(1) // 필요한 경우 적절히 설정
                .memberId(memberVo.getMemberId()) // 추가된 부분: 사용자 ID 설정
                .build();

        log.info("Creating event via AJAX with details: {}", eventVo);
        // 이벤트 생성
        eventService.createEvent(eventVo);
        log.info("Event created successfully via AJAX with ID: {}", eventVo.getEventId());

        return ResponseEntity.ok("Event created successfully");
    }
}
