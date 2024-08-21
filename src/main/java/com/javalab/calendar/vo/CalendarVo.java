package com.javalab.calendar.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 캘린더 도메인 클래스
 * 이 클래스
 * @author sujin
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder

public class CalendarVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int calen_id; // 달력 번호 부여
    private String member_id; // 회원번호
    private int event_id; // 일정번호
    private String calen_name; // 달력 제목
    private String calen_detail; // 달력 상세
    private String calen_color; // 달력색상
    // 날짜 바인딩 패턴 : yyyy-MM-dd
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date calen_create; // 달력 생성 날짜
    private Date calen_update; // 달력 업데이트 날짜

}
