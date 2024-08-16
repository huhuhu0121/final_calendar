package com.javalab.calendar.repository;


import com.javalab.calendar.dto.CalenderDto;
import com.javalab.calendar.dto.EventDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalenderMapper {
    CalenderDto findByUserId(@Param("userId") String userId);  // 사용자 ID로 캘린더 조회
}