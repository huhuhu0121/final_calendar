package com.javalab.calendar.service;

import com.javalab.calendar.repository.EventMapper;
import com.javalab.calendar.vo.EventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {

    EventVo getEvent(int event_id);
    List<EventVo> listEvent();
    int createEvent(EventVo eventVo);
    int updateEvent(EventVo eventVo);
    int deleteEvent(int event_id);
}
