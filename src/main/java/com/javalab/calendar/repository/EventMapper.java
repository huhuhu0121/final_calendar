package com.javalab.calendar.repository;

import com.javalab.calendar.vo.EventVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {
	// 특정 이벤트 조회
	public EventVo getEvent(int event_id);

	// 모든 이벤트 조회
	public List<EventVo> listEvent();

	// 이벤트 생성
	public int createEvent(EventVo eventVo);

	// 이벤트 수정
	public int updateEvent(EventVo eventVo);

	// 이벤트 삭제
	public int deleteEvent(int event_id);

	// selectKey 사용 메소드
	public int createEventSelectKey(EventVo eventVo);
}
