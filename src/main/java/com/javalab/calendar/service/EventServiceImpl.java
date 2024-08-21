package com.javalab.calendar.service;

import com.javalab.calendar.repository.EventMapper;
import com.javalab.calendar.vo.EventVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {

	private final EventMapper eventMapper;

	@Override
	public EventVo getEvent(int event_id) {
		return eventMapper.getEvent(event_id);
	}

	@Override
	public List<EventVo> listEvent() {
		return eventMapper.listEvent();
	}

	@Override
	public int createEvent(EventVo eventVo) {
		return eventMapper.createEvent(eventVo);
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
