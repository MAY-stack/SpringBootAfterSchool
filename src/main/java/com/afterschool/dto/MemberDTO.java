package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
	
	private String userid;
	private String username;
	private String password;
	private String gender;
	private String school;
	private String nickname;
	private String email;
	private String zipcode;
	private String address;
	private String telno;
	private String birthdate;	
	private String role;
	private String verifycode;
	private String company;
	private String storedProfilename;
	private String authkey;
	private String introduceLine;
	private String introduce;
	private String avatar;
	private LocalDateTime signupDate;
	
	public MemberDTO(MemberEntity memberEntity) {
		
		this.userid = memberEntity.getUserid();
		this.username = memberEntity.getUsername();
		this.password = memberEntity.getPassword();
		this.gender = memberEntity.getGender();
		this.school = memberEntity.getSchool();
        this.nickname = memberEntity.getNickname();
		this.email = memberEntity.getEmail();
		this.zipcode = memberEntity.getZipcode();
        this.address = memberEntity.getAddress();
        this.telno = memberEntity.getTelno();
		this.birthdate = memberEntity.getBirthdate();
        this.role = memberEntity.getRole();
		this.verifycode = memberEntity.getVerifycode();
		this.company = memberEntity.getCompany();
		this.storedProfilename = memberEntity.getStoredProfilename();
		this.introduceLine = memberEntity.getIntroduceLine();
		this.introduce = memberEntity.getIntroduce();
		this.avatar = memberEntity.getAvatar();
		this.signupDate = memberEntity.getSignupDate();
				
	}
	
	public MemberEntity dtoToEntity(MemberDTO memberDTO) {
		
		MemberEntity memberEntity = MemberEntity.builder()
											.userid(memberDTO.getUserid())
											.username(memberDTO.getUsername())
											.password(memberDTO.getPassword())
											.gender(memberDTO.getGender())
											.school(memberDTO.getSchool())
                                            .nickname(memberDTO.getNickname())
											.email(memberDTO.getEmail())
                                            .zipcode(memberDTO.getZipcode())
											.address(memberDTO.getAddress())
                                            .telno(memberDTO.getTelno())
											.birthdate(memberDTO.getBirthdate())
                                            .role(memberDTO.getRole())
											.verifycode(memberDTO.getVerifycode())
											.company(memberDTO.getCompany())
											.storedProfilename(memberDTO.getStoredProfilename())
											.introduceLine(memberDTO.getIntroduceLine())
											.introduce(memberDTO.getIntroduce())
											.avatar(memberDTO.getAvatar())
											.signupDate(memberDTO.getSignupDate())
											.build();
		return memberEntity;
	}
	
}