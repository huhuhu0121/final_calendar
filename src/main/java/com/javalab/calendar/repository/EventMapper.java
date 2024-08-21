package com.javalab.calendar.repository;

import com.javalab.calendar.vo.EventVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventMapper {

	// 특정 ID의 이벤트 조회
	EventVo getEvent(@Param("eventId") int eventId);

	// 모든 이벤트 조회
	List<EventVo> listEvent();

	// 새로운 이벤트 생성
	int createEvent(EventVo eventVo);

	// 이벤트 수정
	int updateEvent(EventVo eventVo);

	// 이벤트 삭제
	int deleteEvent(@Param("eventId") int eventId);
}
