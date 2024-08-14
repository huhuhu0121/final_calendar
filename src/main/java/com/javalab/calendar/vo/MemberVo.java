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
public class MemberVo implements Serializable{
	private static final long serialVersionUID = 1L;

	private int member_Id;	// 아이디가 아니고 bno같이 멤버 번호임 nn(not null)
	private int code_id;	// 친구코드 공유 코드 nn
	private String email;	// 이메일로 로그인 해서 이게 결국에는 아이디 nn
	private String name;	// 이름 nn
	private String password;	// 비밀번호 nn
	private String gender;		// 성별 nn
	private Date birth;			// 생년월일 nn
	private String image;		// 프로필 사진 n(null)
	private String bio;			// 자기소개 n

	private Map<String, Object> attributes = Map.of(); // 소셜 로그인 정보 기본 값 설정

	public MemberVo() {
	}

	// 모든 필드를 포함하는 생성자 추가
	public MemberVo(int member_Id,
					int code_id,
					String email,
					String name,
					String password,
					String gender,
					Date birth,
					String image,
					String bio,
					Map<String, Object> attributes
					) {

		this.member_Id = member_Id;
		this.code_id = code_id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		this. image = image;
		this. bio = bio;
		this.attributes = attributes;
	}

}
