package com.afterschool.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.AddrEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.SchoolEntity;

public interface MemberService {

		//아이디 중복 체크. 카운터가 0이면 아이디 사용 가능, 1이면 기존 사용 중인 아이디
		public int idCheck(String userid);

		//별명 중복 체크. 카운터가 0이면 아이디 사용 가능, 1이면 기존 사용 중인 아이디
		public int nnCheck(String nickName);
		
		//이메일 중복 체크. 카운터가 0이면 이메일 사용 가능, 1이면 기존 사용 중인 이메일
		public int emailCheck(String email);
		
		//사용자 정보 보기
		public MemberDTO memberInfo(String userid);
		
		//사용자등록
		public void signup(MemberDTO member);
		
		//사용자 패스워드 수정
		public void memberPasswordModify(String userid,String Password);
		
		//사용자 자동 로그인을 위한 authkey 등록
		public void authkeyUpdate(MemberDTO member);
		
		//사용자 자동 로그인을 위한 authkey로 사용자 정보 가져 오기 
		public MemberEntity memberInfoByAuthkey(String authkey);
		
		//아이디 찾기
		public String memberSearchID(MemberDTO member);
		
		//임시패스워드 생성
		public String tempPassowrdMaker();
		
		//주소 검색
		public Page<AddrEntity> addrSearch(int pageNum, int postNum, String addrSearch);
		
		//학교 검색
		public Page<SchoolEntity> schoolSearch(int pageNum, int postNum, String schoolSearch);
		
		//모든 회원 정보 검색
		public List<MemberEntity> getMember(String role);
		
		//회원 탈퇴
		public void memberInfoDelete(String userid);
	
}
