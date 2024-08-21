package com.javalab.calendar.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder

public class RoutineVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String memberId;
    private int routine_Id;
    private String routine_Title;
    private Date routine_Start;
    private Date routine_End;
    private String routine_Pattern;
    private Date routine_Time;
    private int routine_Manual;
    Map<String, Object> attributes = Map.of();

    private RoutineVo() {
    }

    public RoutineVo(String memberId,
                     int routine_Id,
                     String routine_Title,
                     Date routine_Start,
                     Date routine_End,
                     String routine_Pattern,
                     Date routine_Time,
                     int routine_Manual,
                     Map<String, Object> attributes) {

    this.memberId = memberId;
    this.routine_Id = routine_Id;
    this.routine_Start = routine_Start;
    this.routine_End = routine_End;
    this.routine_Pattern = routine_Pattern;
    this.routine_Time = routine_Time;
    this.routine_Manual = routine_Manual;
    this.attributes = attributes;
    }

}
