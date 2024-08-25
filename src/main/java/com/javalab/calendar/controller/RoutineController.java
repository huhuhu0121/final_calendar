package com.javalab.calendar.controller;

import com.javalab.calendar.service.RoutineService;
import com.javalab.calendar.vo.RoutineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    // 루틴 생성
    @PostMapping
    public ResponseEntity<RoutineVo> createRoutine(@RequestBody Map<String, Object> routineData) {
        try {
            String memberId = (String) routineData.get("memberId");
            String routineTitle = (String) routineData.get("routineTitle");
            Date routineStart = new Date((Long) routineData.get("routineStart"));
            Date routineEnd = new Date((Long) routineData.get("routineEnd"));
            String routinePattern = (String) routineData.get("routinePattern");
            Date routineTime = new Date((Long) routineData.get("routineTime"));
            int routineManual = (int) routineData.get("routineManual");
            Map<String, Object> attributes = (Map<String, Object>) routineData.get("attributes");

            RoutineVo newRoutine = routineService.createRoutine(memberId, routineTitle, routineStart, routineEnd, routinePattern, routineTime, routineManual, attributes);
            return new ResponseEntity<>(newRoutine, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 루틴 조회 (ID로 조회)
    @GetMapping("/{routineId}")
    public ResponseEntity<RoutineVo> getRoutineById(@PathVariable("routineId") int routineId) {
        RoutineVo routine = routineService.getRoutineById(routineId);
        return routine != null ? new ResponseEntity<>(routine, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 루틴 조회 (사용자 ID로 조회)
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<RoutineVo>> getRoutinesByMemberId(@PathVariable("memberId") String memberId) {
        List<RoutineVo> routines = routineService.getRoutinesByMemberId(memberId);
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }

    // 루틴 업데이트
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineVo> updateRoutine(@PathVariable("routineId") int routineId, @RequestBody Map<String, Object> routineData) {
        try {
            String routineTitle = (String) routineData.get("routineTitle");
            Date routineStart = new Date((Long) routineData.get("routineStart"));
            Date routineEnd = new Date((Long) routineData.get("routineEnd"));
            String routinePattern = (String) routineData.get("routinePattern");
            Date routineTime = new Date((Long) routineData.get("routineTime"));
            int routineManual = (int) routineData.get("routineManual");
            Map<String, Object> attributes = (Map<String, Object>) routineData.get("attributes");

            RoutineVo updatedRoutine = routineService.updateRoutine(routineId, routineTitle, routineStart, routineEnd, routinePattern, routineTime, routineManual, attributes);
            return updatedRoutine != null ? new ResponseEntity<>(updatedRoutine, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 루틴 삭제
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable("routineId") int routineId) {
        boolean deleted = routineService.deleteRoutine(routineId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 모든 루틴 조회
    @GetMapping
    public ResponseEntity<List<RoutineVo>> getAllRoutines() {
        List<RoutineVo> routines = routineService.getAllRoutines();
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }
}
