package com.javalab.calendar.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class MemberVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String memberId;    // 회원 고유 번호 (PK)
	private int codeId;         // 친구코드 공유 코드
	private String email;       // 이메일 (로그인 아이디로 사용)
	private String name;        // 이름
	private String password;    // 비밀번호
	private String gender;      // 성별
	private Date birth;         // 생년월일
	private String image;       // 프로필 사진 (nullable)
	private String bio;         // 자기소개 (nullable)
	private int social;         // 소셜 로그인 여부 (0: 일반, 1: 소셜)

	private Map<String, Object> attributes;  // 소셜 로그인 정보

	public MemberVo() {
		this.social = 0;  // 기본값 설정
		this.attributes = Map.of();  // 빈 Map으로 초기화
	}

	@Builder
	public MemberVo(String memberId,
					int codeId,
					String email,
					String name,
					String password,
					String gender,
					Date birth,
					String image,
					String bio,
					int social,
					Map<String, Object> attributes) {

		this.memberId = memberId;
		this.codeId = codeId;
		this.email = email;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		this.image = image;
		this.bio = bio;
		this.social = social;
		this.attributes = attributes != null ? attributes : Map.of();
	}

	public boolean isSocialUser() {
		return this.social == 1;
	}
}