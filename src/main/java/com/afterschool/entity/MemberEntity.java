package com.afterschool.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//insert, update 시 null값은 제외
@DynamicInsert
@DynamicUpdate

@Entity(name="member")
@Table(name="tbl_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MemberEntity {

	@Id
    @Column(name="userid", length=50)
    private String userid;

    @Column(name="password", length=200, nullable=false)
    private String password;

    @Column(name="username", length=20, nullable=false)
    private String username;

    @Column(name="school", length=50, nullable=true)
    private String school;

    @Column(name="nickname", length=50, nullable=true, unique=true)
    private String nickname;

    @Column(name="telno", length=20, nullable=false)
    private String telno;

    @Column(name="gender", length=20, nullable=false)
    private String gender;

    @Column(name="email", length=50, nullable=false, unique=true)
    private String email;

    @Column(name="zipcode", length=20, nullable=true)
    private String zipcode;

    @Column(name="address", length=200, nullable=true)
    private String address;

    @Column(name="birthdate", length=20, nullable=true)
    private String birthdate;

    @Column(name="role", length=20, nullable=false)
    private String role;

    @Column(name="verifycode", length=50, nullable=true)
    private String verifycode;

    @Column(name="company", length=20, nullable=true)
    private String company;

    @Column(name="avatar", length=2, nullable=true)
    private String avatar;

    @Column(name="stored_profilename", length=50, nullable=true)
    private String storedProfilename;
    
    @Column(name="signup_date", length=20, nullable=true)
    private LocalDateTime signupDate;
    
	@Column(name="authkey",length=200, nullable=true)
	private String authkey;
	
	@Column(name="introduce_line", length=500, nullable=true)
	private String introduceLine;
	
	@Column(name="introduce", length=2000, nullable=true)
	private String introduce;
	
	//추가
	@Column(name="main_subject",length=2, nullable=true)
	private String mainSubject;
}
