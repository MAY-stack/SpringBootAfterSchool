package com.afterschool.controller;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.repository.FreeBoardRepository;
import com.afterschool.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class RESTController {

	private final MemberService service;
	private final BCryptPasswordEncoder pwdEncoder;
	
	private final FreeBoardRepository freeBoardRepository;
	
	//restapi test
	@GetMapping("/restapi")
	public String getRestApiTest() throws Exception{
		return "right!";
	}
	
	//메인 자유게시판 최근글 목록
	@GetMapping("/restapi/mainFreeBoardList")
	public List<FreeBoardEntity> getMainFreeBoardList() throws Exception{
		List<FreeBoardEntity> FreeBoardList = freeBoardRepository.getRecent5FreeBoard();
		return FreeBoardList;
	}
	
	//로그인 처리 - 실질은 Security에서 처리 (완)
	@PostMapping("/restapi/account/login")
	public void postLogin(Model model) {}
	
	//로그인 정보 확인 (완)
	@ResponseBody
	@PostMapping("/restapi/account/loginCheck")
	public String postLogIn(MemberDTO loginData,HttpSession session) {

		//아이디 존재 여부 확인
		if(service.idCheck(loginData.getUserid()) == 0)
			return "{\"msg\":\"ID_NOT_FOUND\"}";

		//아이디가 존재하면 읽어온 userid로 로그인 정보 가져 오기
		MemberDTO member = service.memberInfo(loginData.getUserid());

		//패스워드 확인
		if(!pwdEncoder.matches(loginData.getPassword(),member.getPassword())) 
			return "{\"msg\":\"PASSWORD_NOT_FOUND\"}";	
		return "{\"msg\":\"good\"}";
	}
}
