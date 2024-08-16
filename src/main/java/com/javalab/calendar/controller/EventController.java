package com.javalab.calendar.controller;

import com.javalab.calendar.service.EventService;
import com.javalab.calendar.vo.EventVo;
import com.javalab.calendar.vo.MemberVo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/event")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    // 일정 목록 저장용 변수 초기화
    private List<EventVo> eventList = new ArrayList<>();

    /**
     * 일정 내용 보기
     * @param model
     * @return
     */
    @GetMapping("/detail.do/{event_id}")
    public String detail(@PathVariable("event_id") int event_id,
                         Model model) {
        Optional<EventVo> optionalEventVo = eventList.stream()
                .filter(calendar -> calendar.getEvent_id() == event_id)
                .findFirst();

        if (optionalEventVo.isPresent()) {
            EventVo eventVo = optionalEventVo.get();
            model.addAttribute("eventVo", eventVo);
        } else {
            return "redirect:/event/list.do";
        }
        return "event/eventDetail";
    }

    /**
     * 일정 목록 보기
     */
    @GetMapping("/list.do")
    public String list(Model model) {
        model.addAttribute("eventList", eventList);
        return "event/eventList";
    }

    /**
     * 일정 등록 폼 보기
     */
    @GetMapping("/create.do")
    public String create(Model model) {
        model.addAttribute("eventVo", new EventVo());
        return "event/eventCreate";
    }

    /**
     * 일정 등록 처리
     * 입력데이터 검증
     * - @Validated : 입력데이터 검증을 위한 어노테이션,
     *
     * @Valid 어노테이션을 사용하여 입력데이터 검증. BoardDto 앞에다 붙여준다
     * BoardDto 바인딩작업을 하면서 동시에 검증을 한다. 그러면서 해당 필드(속성)에
     * 오류가 있으면 그 오류 내용을 BindingResult에 담아준다.
     * - BindingResult : 입력데이터 검증 결과를 담는 객체
     * - #fileds.hasErrors("필드명") : 파라미터로 지정된 필드에 오류가 있는지 검사하는 메서드. 타임리프의 유틸리티 클래스
     * 해당 "필드명"에 오류가 있으면 true, 없으면 false를 반환한다.(화면에서) 오류가 있으면 해당 필드에 대한 오류 메시지를 출력한다.
     * th:errors="*{title}" : title 필드에 대한 오류 메시지를 출력한다.
     * -  @NotBlank > @NotEmplty > @NotNull 검증 강도가 다르다.
     */
    @PostMapping("/create.do")
    public String createEvent(@ModelAttribute("eventVo") EventVo eventVo,
                              @AuthenticationPrincipal MemberVo memberVo,
                              HttpSession session) {

        // 세션에서 조회한 사용자를 작성로 설정
        eventVo.setMemberId(memberVo.getMemberId());

        eventService.createEvent(eventVo);
        return "redirect:/event/list.do";	// 목록 요청(listBoard() 호출)
    }

    /**
     * 일정 수정 폼 보기
     * - 파라미터로 주어진 한개의 게시물을 찾아서 수정폼에 세팅한다.
     */
    @GetMapping("/update.do")
    public String updateForm(@RequestParam("event_id") int event_id,
                             HttpSession session,
                             Model model) {

        EventVo eventVo = eventService.getEvent(event_id);
        model.addAttribute("eventVo", eventVo);    // 화면에 보여줄 게시물을 model에 저장
        return "event/eventUpdate";
    }

    /**
     * 일정 수정 처리
     */
    @PostMapping("/update.do")
    public String update(@ModelAttribute("eventVo") EventVo eventVo,
                         @AuthenticationPrincipal MemberVo memberVo,
                         HttpSession session) {

        // 세션에서 조회한 사용자를 작성로 설정
        eventVo.setMemberId(memberVo.getMemberId());

        eventService.updateEvent(eventVo);
        return "redirect:/event/list.do";	// 목록 요청(listBoard() 호출)
    }

    /**
     * 일정 삭제 처리
     */

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("event_id") int event_id) {
        eventService.deleteEvent(event_id);
        return "redirect:/event/list.do";	// 목록 요청(listBoard() 호출)
    }

}
