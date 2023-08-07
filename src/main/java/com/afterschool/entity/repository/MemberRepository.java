package com.afterschool.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.MemberEntity;
public interface MemberRepository extends JpaRepository<MemberEntity,String>{
	/*
	//Authkey
	public MemberEntity findByAuthkey(String authkey);
	
	//닉네임 중복 체크
	public Optional<MemberEntity> findByNickname(String nickName);
	
	//이메일 중복 체크
	public Optional<MemberEntity> findByEmail(String email);
	
	//아이디 찾기 (by username, email)
	public Optional<MemberEntity> findByUsernameAndEmail(String username, String email);
	
	//Role에 해당하는 멤버 리스트
	public List<MemberEntity> findAllByRole(String role);

	public List<MemberEntity> findByRoleNotLike(String role);*/
	
	

	//Authkey
	public MemberEntity findByAuthkey(String authkey);
	
	//닉네임 중복 체크
	public Optional<MemberEntity> findByNickname(String nickName);
	
	//이메일 중복 체크
	public Optional<MemberEntity> findByEmail(String email);
	
	//아이디 찾기 (by username, email)
	public Optional<MemberEntity> findByUsernameAndEmail(String username, String email);
	
	//Role에 해당하는 멤버 리스트
	public List<MemberEntity> findAllByRole(String role);

	//Role, main subject에 해당하는 멤버 리스트
	public List<MemberEntity> findAllByRoleAndMainSubject(String role, String mainSubject);
	
	//Role, keywor에 해당하는 멤버 리스트
	public List<MemberEntity> findAllByRoleAndUsernameContaining(String role, String keyword);
	
	//Role, main subject, keyword에 해당하는 멤버 리스트
	public List<MemberEntity> findAllByUsernameContainingAndRoleAndMainSubject(String keyword, String role, String mainSubject);
	
	//manager 회원 탈퇴 시키기
	public List<MemberEntity> findByRoleNotLike(String role);
	
}
