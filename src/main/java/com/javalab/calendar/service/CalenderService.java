package com.javalab.calendar.service;


import com.javalab.calendar.dto.CalenderDto;
import com.javalab.calendar.vo.CalenderVo;
import com.javalab.calendar.vo.EventVo;
import jakarta.validation.Valid;

import java.util.List;

public interface CalenderService {
    CalenderDto getCalenderByUserId(String member_Id);  // 사용자 ID로 캘린더 조회
    void createEvent(EventVo eventVo);             // 일정 생성
    List<EventVo> getEventsByCalenderId(int calenId); // 캘린더 ID로 일정 조회
    void updateEvent(@Valid CalenderVo eventVo);             // 일정 수정
    void deleteEvent(int eventId);                   // 일정 삭제

}

