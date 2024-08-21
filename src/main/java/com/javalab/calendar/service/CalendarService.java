//package com.javalab.calendar.service;
//
//
//import com.javalab.calendar.dto.CalendarDto;
//import com.javalab.calendar.vo.CalendarVo;
//import com.javalab.calendar.vo.EventVo;
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public interface CalendarService {
//    CalendarDto getCalendarByUserId(String memberId);  // 사용자 ID로 캘린더 조회
//    void createEvent(EventVo eventVo);             // 일정 생성
//    List<EventVo> getEventsByCalendarId(int calenId); // 캘린더 ID로 일정 조회
//    void updateEvent(@Valid CalendarVo eventVo);             // 일정 수정
//    void deleteEvent(int eventId);                   // 일정 삭제
//
//}
//
