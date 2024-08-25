package com.javalab.calendar.service;

import com.javalab.calendar.vo.RoutineVo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutineServiceImpl implements RoutineService {

    // 루틴을 저장할 저장소 (예: 메모리)
    private final Map<Integer, RoutineVo> routineMap = new HashMap<>();
    private int currentId = 0;  // 루틴 ID를 자동으로 생성하기 위한 변수

    @Override
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

    @Override
    public RoutineVo getRoutineById(int routine_Id) {
        return routineMap.get(routine_Id);
    }

    @Override
    public List<RoutineVo> getRoutinesByMemberId(String memberId) {
        List<RoutineVo> routines = new ArrayList<>();
        for (RoutineVo routine : routineMap.values()) {
            if (routine.getMemberId().equals(memberId)) {
                routines.add(routine);
            }
        }
        return routines;
    }

    @Override
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

    @Override
    public boolean deleteRoutine(int routine_Id) {
        return routineMap.remove(routine_Id) != null;
    }

    @Override
    public List<RoutineVo> getAllRoutines() {
        return new ArrayList<>(routineMap.values());
    }
}
