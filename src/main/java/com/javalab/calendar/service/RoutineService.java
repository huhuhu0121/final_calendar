package com.javalab.calendar.service;

import com.javalab.calendar.vo.RoutineVo;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoutineService {

    RoutineVo createRoutine(String memberId, String routine_Title, Date routine_Start,
                            Date routine_End, String routine_Pattern, Date routine_Time,
                            int routine_Manual, Map<String, Object> attributes);

    RoutineVo getRoutineById(int routine_Id);

    List<RoutineVo> getRoutinesByMemberId(String memberId);

    RoutineVo updateRoutine(int routine_Id, String routine_Title, Date routine_Start,
                            Date routine_End, String routine_Pattern, Date routine_Time,
                            int routine_Manual, Map<String, Object> attributes);

    boolean deleteRoutine(int routine_Id);

    List<RoutineVo> getAllRoutines();
}
