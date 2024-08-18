package com.javalab.calendar.repository;

import com.javalab.calendar.vo.RoutineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoutineMapper {

    // 특정 루틴을 ID로 조회
    RoutineVo selectRoutineById(@Param("routine_Id") int routine_Id);

    // 특정 사용자의 모든 루틴 조회
    List<RoutineVo> selectRoutinesByMemberId(@Param("memberId") String memberId);

    // 루틴 생성
    int insertRoutine(RoutineVo routine);

    // 루틴 수정
    int updateRoutine(RoutineVo routine);

    // 루틴 삭제
    int deleteRoutine(@Param("routine_Id") int routine_Id);
}
