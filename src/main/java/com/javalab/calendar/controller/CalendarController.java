//package com.javalab.calendar.controller;
//
//import com.javalab.calendar.dto.EventDto;
//import com.javalab.calendar.vo.EventVo;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Controller
//@RequestMapping("/calendar")
//@Slf4j
//public class CalendarController {
//
//    // 일정 목록 저장용 변수 초기화
//    private List<EventVo> boardList = new ArrayList<>();
//
//    /**
//     * 일정 내용 보기
//     * @param model
//     * @return
//     */
//    @GetMapping("/detail.do/{event_id}")
//    public String detail(@PathVariable("event_id") int event_id, Model model) {
//        Optional<EventVo> optionalEventVo = eventList.stream()
//                .filter(calendar -> calendar.getEvent_id() == event_id)
//                .findFirst();
//
//        if (optionalEventVo.isPresent()) {
//            EventVo boardVo = optionalEventVo.get(); // get() : Optional 객체에서 BoardVo 객체를 가져옴
//            model.addAttribute("boardVo", boardVo);
//        } else {
//            return "redirect:/board/list.do"; // 목록 페이지로 리다이렉트
//        }
//        return "board/boardDetail"; // 상세 페이지로 이동
//    }
//
//    /**
//     * 게시물 목록 보기
//     */
//    @GetMapping("/list.do")
//    public String list(Model model) {
//        model.addAttribute("boardList", boardList);
//        return "board/boardList";
//    }
//
//    /**
//     * 게시물 등록 폼 보기
//     */
//    @GetMapping("/create.do")
//    public String create(Model model) {
//        model.addAttribute("boardDto", new BoardDto());
//        return "board/boardCreate";
//    }
//
//    /**
//     * 게시물 등록 처리
//     * 입력데이터 검증
//     * - @Validated : 입력데이터 검증을 위한 어노테이션,
//     *   @Valid 어노테이션을 사용하여 입력데이터 검증. BoardDto 앞에다 붙여준다
//     *   BoardDto 바인딩작업을 하면서 동시에 검증을 한다. 그러면서 해당 필드(속성)에
//     *   오류가 있으면 그 오류 내용을 BindingResult에 담아준다.
//     * - BindingResult : 입력데이터 검증 결과를 담는 객체
//     * - #fileds.hasErrors("필드명") : 파라미터로 지정된 필드에 오류가 있는지 검사하는 메서드. 타임리프의 유틸리티 클래스
//     *   해당 "필드명"에 오류가 있으면 true, 없으면 false를 반환한다.(화면에서) 오류가 있으면 해당 필드에 대한 오류 메시지를 출력한다.
//     *   th:errors="*{title}" : title 필드에 대한 오류 메시지를 출력한다.
//     * -  @NotBlank > @NotEmplty > @NotNull 검증 강도가 다르다.
//     */
//    @PostMapping("/create.do")
//    public String create(@ModelAttribute("boardDto") @Valid BoardDto boardDto,
//                         BindingResult bindingResult) {
//        log.info("boardDto: {}", boardDto);
//
//        // 오류 체크
//        if (bindingResult.hasErrors()) {
//            return "board/boardCreate";
//        }
//
//        // 게시물 번호 설정
//        int bno = boardList.size() + 1;
//
//        // 빌더 패턴으로 객체 생성
//        // 화면에서 전달받은 값으로 BoardVo 객체를 생성한다.
//        // boardList<BoardVo>에 추가하기 위해서 변환해야 한다.
//        BoardVo boardVo = BoardVo.builder() // builder() : 빌더패턴을 사용하여 객체 생성(내부 정적 클래스가 생성된다)
//                .bno(bno)   // 내부 정적 클래스의 bno()메소드에 파라미터로 받은 값을 넣어준다.
//                .title(boardDto.getTitle()) // 내부정적 클래스의 title()메소드에 파라미터로 받은 값을 넣어준다.
//                .content(boardDto.getContent())
//                .memberId(boardDto.getMemberId())
//                .hitNo(0)
//                .regDate(boardDto.getRegDate())
//                .build(); // build() : 내부 정적 클래스는 자신이 받은 값들로 원본 객체를 생성해서 반환한다.
//
//        boardList.add(boardVo);
//
//        // 게시물 목록 페이지로 리다이렉트
//        return "redirect:/board/list.do";
//    }
//
//    /**
//     * 게시물 수정 폼 보기
//     * - 파라미터로 주어진 한개의 게시물을 찾아서 수정폼에 세팅한다.
//     */
//    @GetMapping("/update.do/{bno}")
//    public String updateForm(@PathVariable("bno") int bno, Model model) {
//        // 게시물 번호로 게시물(BoardVo)을 찾는다.
//        Optional<BoardVo> optionalBoard = boardList.stream() // Optional은 널 값도 저장할 수 있는 컨테이너
//                .filter(b -> b.getBno() == bno)
//                .findFirst(); // 혹시 여러개가 나와더 첫번째 BoardVo만 취함.
//
//        // 게시물이 존재하면
//        if (optionalBoard.isPresent()) {
//            // 게시물 얻기
//            BoardVo boardVo = optionalBoard.get(); // Optional에서 값을 꺼낸다.
//            // BoardVo -> BoardDto 변환(BoardDto : 화면에서 사용하는 객체)
//            BoardDto boardDto = BoardDto.builder() // BoarDto를 목록에서 꺼낸 값들로 채운다.
//                    .bno(boardVo.getBno())
//                    .title(boardVo.getTitle())
//                    .content(boardVo.getContent())
//                    .memberId(boardVo.getMemberId())
//                    .hitNo(boardVo.getHitNo())
//                    .regDate(boardVo.getRegDate())
//                    .build();
//
//            // Dto를 model에 저장
//            model.addAttribute("boardDto", boardDto);
//            return "board/boardUpdate";
//        } else {
//            return "redirect:/board/list.do";
//        }
//    }
//
//    /**
//     * 게시물 수정 처리
//     */
//    @PostMapping("/update.do/{bno}") // "/update.do/1"
//    public String update(@PathVariable("bno") int bno,
//                         @ModelAttribute("boardDto") @Valid BoardDto boardDto,
//                         BindingResult bindingResult) {
//
//        log.info("boardDto: {}", boardDto);
//
//        if (bindingResult.hasErrors()) { // 오류가 하나라도 있으면 수정화면으로 이동
//            return "board/boardUpdate";
//        }
//
//        // 수정할 bno로 목록에서 조회
//        boardList = boardList.stream()
//                .map(b -> { // map 함수는 스트림의 각 요소를 다른 객체로 매핑하는 데 사용되며, return을 사용하여 새 객체를 반환해야 한다.
//                    if (b.getBno() == bno) {
//                        // [Dto -> Vo] 목록에서 꺼내온 BoardVo에다 화면에서 받은 Dto의 값으로 채워넣는다.
//                        BoardVo boardVo = BoardVo.builder()
//                                .bno(bno)
//                                .title(boardDto.getTitle())
//                                .content(boardDto.getContent())
//                                .memberId(boardDto.getMemberId())
//                                .hitNo(b.getHitNo())
//                                .regDate(boardDto.getRegDate())
//                                .build();
//                        return boardVo; // 화면의 값으로 세팅된 BoardVo를 반환한다.
//                    }
//                    return b;   // 그 외 게시물들은 그냥 반환해서 새로운 ArrayList에 저장됨.
//                })
//                .collect(Collectors.toList()); // collect는 스트림의 요소를 수집하는 최종 연산이다.
//        return "redirect:/board/list.do";
//    }
//
//    /**
//     * 게시물 삭제 처리
//     */
//    @PostMapping("/delete.do/{bno}")
//    public String delete(@PathVariable("bno") int bno) {
//        log.info("Deleting board with bno: {}", bno);
//
//        // 게시물 삭제 처리 로직
//        boardList = boardList.stream()
//                .filter(b -> b.getBno() != bno)
//                .collect(Collectors.toList());
//
//        // 게시물 목록 페이지로 리다이렉트
//        return "redirect:/board/list.do";
//    }
//}
