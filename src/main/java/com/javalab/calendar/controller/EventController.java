package com.javalab.calendar.controller;

import com.javalab.calendar.dto.EventDto;
import com.javalab.calendar.vo.EventVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/event")
@Slf4j
public class EventController {

    // 일정 목록 저장용 변수 초기화
    private List<EventVo> eventList = new ArrayList<>();

    /**
     * 일정 내용 보기
     * @param model
     * @return
     */
    @GetMapping("/detail.do/{event_id}/{member_id}")
    public String detail(@PathVariable("event_id") int event_id,
                         @PathVariable("member_id") int member_id,
                         Model model) {
        Optional<EventVo> optionalEventVo = eventList.stream()
                .filter(calendar -> calendar.getEvent_id() == event_id && calendar.getMember_id() == member_id)
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
        model.addAttribute("eventDto", new EventDto());
        return "event/eventCreate";
    }

    /**
     * 일정 등록 처리
     * 입력데이터 검증
     * - @Validated : 입력데이터 검증을 위한 어노테이션,
     *   @Valid 어노테이션을 사용하여 입력데이터 검증. BoardDto 앞에다 붙여준다
     *   BoardDto 바인딩작업을 하면서 동시에 검증을 한다. 그러면서 해당 필드(속성)에
     *   오류가 있으면 그 오류 내용을 BindingResult에 담아준다.
     * - BindingResult : 입력데이터 검증 결과를 담는 객체
     * - #fileds.hasErrors("필드명") : 파라미터로 지정된 필드에 오류가 있는지 검사하는 메서드. 타임리프의 유틸리티 클래스
     *   해당 "필드명"에 오류가 있으면 true, 없으면 false를 반환한다.(화면에서) 오류가 있으면 해당 필드에 대한 오류 메시지를 출력한다.
     *   th:errors="*{title}" : title 필드에 대한 오류 메시지를 출력한다.
     * -  @NotBlank > @NotEmplty > @NotNull 검증 강도가 다르다.
     */
    @PostMapping("/create.do")
    public String create(@ModelAttribute("eventDto") @Valid EventDto eventDto,
                         BindingResult bindingResult) {
        log.info("eventDto: {}", eventDto);

        // 오류 체크
        if (bindingResult.hasErrors()) {
            return "event/eventCreate";
        }

        // 로그인한 사용자의 member_id 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 또는 사용자 ID
        int member_id = getMemberIdByUsername(username); // 사용자 이름으로 member_id를 얻는 로직 필요

        // 게시물 번호 설정
        int event_id = eventList.size() + 1;

        // 빌더 패턴으로 객체 생성
        // 화면에서 전달받은 값으로 BoardVo 객체를 생성한다.
        // boardList<BoardVo>에 추가하기 위해서 변환해야 한다.
        EventVo eventVo = EventVo.builder() // builder() : 빌더패턴을 사용하여 객체 생성(내부 정적 클래스가 생성된다)
                .event_id(event_id)   // 내부 정적 클래스의 bno()메소드에 파라미터로 받은 값을 넣어준다.
                .member_id(member_id)
                .title(eventDto.getTitle()) // 내부정적 클래스의 title()메소드에 파라미터로 받은 값을 넣어준다.
                .category_id(eventDto.getCategory_id())
                .memo(eventDto.getMemo())
                .start_date(eventDto.getStart_date())
                .end_date(eventDto.getEnd_date())
                .build(); // build() : 내부 정적 클래스는 자신이 받은 값들로 원본 객체를 생성해서 반환한다.

        eventList.add(eventVo);

        // 게시물 목록 페이지로 리다이렉트
        return "redirect:/event/list.do";
    }

    /**
     * 일정 수정 폼 보기
     * - 파라미터로 주어진 한개의 게시물을 찾아서 수정폼에 세팅한다.
     */
    @GetMapping("/update.do/{event_id}/{member_id}")
    public String updateForm(@PathVariable("event_id") int event_id,
                             @PathVariable("member_id") int member_id,
                             Model model) {
        Optional<EventVo> optionalEvent = eventList.stream()
                .filter(e -> e.getEvent_id() == event_id && e.getMember_id() == member_id)
                .findFirst();

        if (optionalEvent.isPresent()) {
            EventVo eventVo = optionalEvent.get();
            EventDto eventDto = EventDto.builder()
                    .event_id(eventVo.getEvent_id())
                    .event_id(eventVo.getMember_id())
                    .title(eventVo.getTitle())
                    .category_id(eventVo.getCategory_id())
                    .memo(eventVo.getMemo())
                    .start_date(eventVo.getStart_date())
                    .end_date(eventVo.getEnd_date())
                    .build();

            model.addAttribute("eventDto", eventDto);
            return "event/eventUpdate";
        } else {
            return "redirect:/event/list.do";
        }
    }

    /**
     * 일정 수정 처리
     */
    @PostMapping("/update.do/{event_id}/{member_id}")
    public String update(@PathVariable("event_id") int event_id,
                         @PathVariable("member_id") int member_id,
                         @ModelAttribute("eventDto") @Valid EventDto eventDto,
                         BindingResult bindingResult) {

        log.info("eventDto: {}", eventDto);

        if (bindingResult.hasErrors()) {
            return "event/eventUpdate";
        }

        eventList = eventList.stream()
                .map(e -> {
                    if (e.getEvent_id() == event_id && e.getMember_id() == member_id) {
                        EventVo eventVo = EventVo.builder()
                                .event_id(event_id)
                                .member_id(member_id)
                                .title(eventDto.getTitle())
                                .category_id(eventDto.getCategory_id())
                                .memo(eventDto.getMemo())
                                .start_date(eventDto.getStart_date())
                                .end_date(eventDto.getEnd_date())
                                .build();
                        return eventVo;
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return "redirect:/event/list.do";
    }

    /**
     * 일정 삭제 처리
     */
    @PostMapping("/delete.do/{event_id}/{member_id}")
    public String delete(@PathVariable("event_id") int event_id,
                         @PathVariable("member_id") int member_id) {
        log.info("Deleting event with event_id: {}, member_id: {}", event_id, member_id);

        eventList = eventList.stream()
                .filter(c -> c.getEvent_id() != event_id || c.getMember_id() != member_id)
                .collect(Collectors.toList());

        return "redirect:/event/list.do";
    }

    /**
     * 현재 로그인한 사용자의 사용자 ID(member_id)를 가져오는 메서드
     * 이 메서드는 실제로 데이터베이스 또는 서비스에서 사용자를 조회하여 member_id를 가져오는 로직을 구현해야 합니다.
     */
    private int getMemberIdByUsername(String username) {
        // TODO: 사용자 이름으로 사용자 ID(member_id)를 가져오는 로직을 구현하세요.
        // 예시: return userService.getMemberIdByUsername(username);
        return 1; // 예시로 임시 member_id 반환
    }
}
