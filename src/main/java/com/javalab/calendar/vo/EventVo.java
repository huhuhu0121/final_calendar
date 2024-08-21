package com.javalab.calendar.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 일정 도메인 클래스
 * - EventVo: 데이터베이스의 EVENT 테이블과 매핑되는 객체
 * - 데이터베이스의 상태를 그대로 반영하며, 이를 통해 데이터베이스로부터 데이터를 읽거나 쓸 수 있음
 */
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
@NoArgsConstructor  // 파라미터 없는 기본 생성자 생성
@Getter @Setter     // 각 필드에 대한 getter와 setter 메서드 생성
@ToString           // toString 메서드 자동 생성
@Builder            // 빌더 패턴 구현
public class EventVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int eventId;        // 일정 번호 (PK)
    private String memberId;    // 회원 번호 (FK)
    private int categoryId;     // 카테고리 번호 (FK)
    private int routineId;      // 루틴 번호 (FK)
    private String title;       // 일정 제목
    private String location;    // 일정 위치

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;     // 일정 시작일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;       // 일정 종료일

    private String memo;        // 일정 메모
    private int allDay;         // 종일 여부 (1: 종일, 0: 시간 지정)
    private int publice;        // 공개 여부 (1: 공개, 0: 비공개)
}