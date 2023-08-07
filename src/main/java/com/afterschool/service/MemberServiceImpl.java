package com.afterschool.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.AddrEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.SchoolEntity;
import com.afterschool.entity.repository.AddrRepository;
import com.afterschool.entity.repository.MemberRepository;
import com.afterschool.entity.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository memberRepository;
	private final SchoolRepository schoolRepository;
	private final AddrRepository addrRepository;
	private final BCryptPasswordEncoder pwdEncoder; 
	
	//아이디 중복 체크. 카운터가 0이면 아이디 사용 가능, 1이면 기존 사용 중인 아이디
	@Override
	public int idCheck(String userid) {
		return memberRepository.findById(userid).isEmpty()?0:1;
	}
	
	//별명 중복 체크. 카운터가 0이면 아이디 사용 가능, 1이면 기존 사용 중인 닉네임
	public int nnCheck(String nickname) {
		return memberRepository.findByNickname(nickname).isEmpty()?0:1;
	};
	
	//이메일 중복 체크. 카운터가 0이면 이메일 사용 가능, 1이면 기존 사용 중인 이메일
	public int emailCheck(String email) {
		return memberRepository.findByEmail(email).isEmpty()?0:1;
	};
	
	//사용자 정보 가져 오기
	@Override
	public MemberDTO memberInfo(String userid) {
		return memberRepository.findById(userid).map(member-> new MemberDTO(member)).get();
	}

	//사용자등록
	@Override
	public void signup(MemberDTO member) {
		member.setSignupDate(LocalDateTime.now());
		memberRepository.save(member.dtoToEntity(member));	
	}
	
	//사용자 패스워드 수정
	@Override
	public void memberPasswordModify(String userid,String password) {
		MemberEntity memberEntity = memberRepository.findById(userid).get();
		memberEntity.setPassword(pwdEncoder.encode(password));
		memberRepository.save(memberEntity);
	}
	
	//사용자 자동 로그인을 위한 authkey 등록
	@Override
	public void authkeyUpdate(MemberDTO member) {
		MemberEntity memberEntity = memberRepository.findById(member.getEmail()).get();
		//memberEntity.setAuthkey(member.getAuthkey());
		memberRepository.save(memberEntity);
	}
	
	//사용자 자동 로그인을 위한 authkey로 사용자 정보 가져 오기 
	@Override
	public MemberEntity memberInfoByAuthkey(String authkey) {
		return memberRepository.findByAuthkey(authkey);
	}
	
	//아이디 찾기
	@Override
	public String memberSearchID(MemberDTO member) {
		return memberRepository.findByUsernameAndEmail(member.getUsername(),
				member.getEmail()).map(m-> m.getUserid()).orElse("ID_NOT_FOUND");	 
	}
	
	//임시패스워드 생성
	@Override
	public String tempPassowrdMaker() {
		
		//숫자 + 영문대소문자 7자리 임시패스워드 생성
		StringBuffer tempPW = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 7; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z : 아스키코드 97~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z : 아스키코드 65~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		    	tempPW.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		return tempPW.toString();
	};
	
	//주소 검색
	@Override
	public Page<AddrEntity> addrSearch(int pageNum, int postNum, String addrSearch){
		PageRequest pageRequest = PageRequest.of(pageNum-1, postNum,Sort.by(Direction.ASC,"zipcode"));
		return addrRepository.findByRoadContainingOrBuildingContaining(addrSearch, addrSearch, pageRequest);
	}
	
	//학교 검색
	@Override
	public Page<SchoolEntity> schoolSearch(int pageNum, int postNum, String schoolSearch) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, postNum);
		return schoolRepository.findBySchoolNameContaining(schoolSearch, pageRequest);
	}
	
	//모든 회원 정보 검색
	@Override
	public List<MemberEntity> getMember(String role) {
		return memberRepository.findByRoleNotLike(role);
	}
	
	//회원 탈퇴
	@Override
	public void memberInfoDelete(String userid) {
		MemberEntity memberEntity = memberRepository.findById(userid).get();
		memberRepository.delete(memberEntity);
	}
}
