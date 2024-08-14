package com.javalab.calendar.service;

import com.javalab.calendar.repository.EventMapper;
import com.javalab.calendar.repository.MemberMapper;
import com.javalab.calendar.vo.EventVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper eventMapper;

	// 멤버 매퍼 인터페이스
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public List<EventVo> listEvent() {
		return eventMapper.listEvent();
	}

	@Override
	public EventVo getEvent(int event_id) {
		EventVo eventVo = eventMapper.getEvent(event_id);
		return eventVo;
	}

	@Override
	@Transactional
	public int createEvent(EventVo eventVo) {
		// 이벤트 저장 처리
		// int result = eventMapper.createEvent(eventVo);	// 오리지널

		log.info("일정 저장전 event_id : " + eventVo.getEvent_id());	// 0

		int result = eventMapper.createEventSelectKey(eventVo); // selectKey 사용

		log.info("일정 저장후 event_id : " + eventVo.getEvent_id());	// ?

		if (result > 0) {
		}
		return result;
	}

	@Override
	public int updateEvent(EventVo eventVo) {
		return eventMapper.updateEvent(eventVo);
	}

	@Override
	public int deleteEvent(int event_id) {
		return eventMapper.deleteEvent(event_id);
	}
}
