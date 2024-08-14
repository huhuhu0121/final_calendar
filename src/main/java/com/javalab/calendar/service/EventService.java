package com.javalab.calendar.service;

import com.javalab.calendar.vo.EventVo;
import com.javalab.calendar.vo.MemberVo;

import java.util.List;

public interface EventService {

    public EventVo getEvent(int event_Id);

    // 모든 이벤트 조회
    public List<EventVo> listEvent();

    // 이벤트 생성
    public int createEvent(EventVo eventVo);

    // 이벤트 수정
    public int updateEvent(EventVo eventVo);

    // 이벤트 삭제
    public int deleteEvent(int event_id);
}
