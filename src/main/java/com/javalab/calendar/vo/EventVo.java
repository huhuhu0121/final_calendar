package com.javalab.calendar.vo;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 일정 도메인 클래스
 * - EventVo: 이는 보통 데이터베이스와 직접 상호작용하는 엔티티로, 데이터베이스의 테이블과
 *   매핑되는 객체이다. 데이터베이스의 상태를 그대로 반영하며, 이를 통해 데이터베이스로부터
 *   데이터를 읽거나 쓸 수 있다. 데이터베이스 테이블과 거의 똑같다고 보면 된다.
 * [빌더패턴]
 * EventDto: 빌더 패턴을 사용하여 객체 생성 시, 가독성과 유지보수성을 높일 수 있다.
 * AllArgsConstructor : 모든 필드를 파라미터로 받는 생성자를 만들어준다. 빌더패턴 사용시 필요함.
 *                      빌더패턴 사용시 모든 필드를 파라미터로 받는 생성자를 이용해서 객체를 생성함.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class EventVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int event_id;			// 일정번호 - 캘린더에서 불러올때는 일정 ID 받아서
    private String memberId;        // 회원번호
    private int category_id;    // 카테고리번호
    private int routine_id;     // 루틴번호
    private String title;       // 일정 제목
    private String location;    // 일정 위치
    // 날짜 바인딩 패턴 : yyyy-MM-dd
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start_date;    // 일정 시작일
    private Date end_date;      // 일정 종료일
    private String memo;        // 일정 메모
    private int all_day;        // 시간대 설정
    private int publice;        // 공개여부 1이면 공개, 0이면 비공개

    public void setMemberId(String memberId) {
    }
}
