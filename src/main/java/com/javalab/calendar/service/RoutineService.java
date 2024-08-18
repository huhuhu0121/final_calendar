package com.javalab.calendar.service;

import com.javalab.calendar.vo.RoutineVo;
import java.util.*;

public class RoutineService {
    // 루틴을 저장할 저장소 (예: 메모리, 데이터베이스)
    private final Map<Integer, RoutineVo> routineMap = new HashMap<>();
    private int currentId = 0;  // 루틴 ID를 자동으로 생성하기 위한 변수

    // 루틴 생성
    public RoutineVo createRoutine(String memberId, String routine_Title, Date routine_Start,
                                   Date routine_End, String routine_Pattern, Date routine_Time,
                                   int routine_Manual, Map<String, Object> attributes) {
        RoutineVo newRoutine = RoutineVo.builder()
                .memberId(memberId)
                .routine_Id(++currentId)
                .routine_Title(routine_Title)
                .routine_Start(routine_Start)
                .routine_End(routine_End)
                .routine_Pattern(routine_Pattern)
                .routine_Time(routine_Time)
                .routine_Manual(routine_Manual)
                .attributes(attributes)
                .build();

        routineMap.put(newRoutine.getRoutine_Id(), newRoutine);
        return newRoutine;
    }

    // 루틴 조회 (ID로 조회)
    public RoutineVo getRoutineById(int routine_Id) {
        return routineMap.get(routine_Id);
    }

    // 루틴 조회 (사용자 ID로 조회)
    public List<RoutineVo> getRoutinesByMemberId(String memberId) {
        List<RoutineVo> routines = new ArrayList<>();
        for (RoutineVo routine : routineMap.values()) {
            if (routine.getMemberId().equals(memberId)) {
                routines.add(routine);
            }
        }
        return routines;
    }

    // 루틴 업데이트
    public RoutineVo updateRoutine(int routine_Id, String routine_Title, Date routine_Start,
                                   Date routine_End, String routine_Pattern, Date routine_Time,
                                   int routine_Manual, Map<String, Object> attributes) {
        RoutineVo routine = routineMap.get(routine_Id);
        if (routine != null) {
            routine.setRoutine_Title(routine_Title);
            routine.setRoutine_Start(routine_Start);
            routine.setRoutine_End(routine_End);
            routine.setRoutine_Pattern(routine_Pattern);
            routine.setRoutine_Time(routine_Time);
            routine.setRoutine_Manual(routine_Manual);
            routine.setAttributes(attributes);
        }
        return routine;
    }

    // 루틴 삭제
    public boolean deleteRoutine(int routine_Id) {
        return routineMap.remove(routine_Id) != null;
    }

    // 모든 루틴 조회
    public List<RoutineVo> getAllRoutines() {
        return new ArrayList<>(routineMap.values());
    }
}
