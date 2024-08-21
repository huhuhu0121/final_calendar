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

	private String memberId;;	// 아이디가
	private int code_id;	// 친구코드 공유 코드 	nn
	private String email;	// 이메일로 로그인 해서 이게 결국에는 아이디 nn
	private String name;	// 이름 nn
	private String password;	// 비밀번호 nn
	private String gender;		// 성별 nn
	private Date birth;			// 생년월일 nn
	private String image;		// 프로필 사진 n(null)
	private String bio;			// 자기소개 n
	private int social = 0; // 기본 값 설정 (0 = 일반, 1 = 소셜)

	@Builder.Default
	private List<Role> roles = new ArrayList<>(); // MemberRole 객체 리스트

	private Map<String, Object> attributes = Map.of(); // 소셜 로그인 정보 기본 값 설정

	public MemberVo() {
	}

	// 모든 필드를 포함하는 생성자 추가
	public MemberVo(String memberId,
					int code_id,
					String email,
					String name,
					String password,
					String gender,
					Date birth,
					String image,
					String bio,
					int social,
					List<Role> roles,
					Map<String, Object> attributes) {

		this.memberId = memberId;
		this.code_id = code_id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		this.image = image;
		this.bio = bio;
		this.social = social;
		this.roles = roles;
		this.attributes = attributes;
	}

	public boolean isSocialUser() {
		return this.social == 1;
	}
}
