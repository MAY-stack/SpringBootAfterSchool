package com.afterschool.controller;


import java.io.File;
import java.net.URLEncoder;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.AddrEntity;
import com.afterschool.entity.SchoolEntity;
import com.afterschool.service.MemberService;
import com.afterschool.util.PageUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {
	
	private final MemberService service;
	private final BCryptPasswordEncoder pwdEncoder; 	
	
	/*GET*/	
	//로그인 화면 보기 (완)
	@GetMapping("/account/login")
	public void getLogin(){}
	
	//아이디찾기 화면 (완)
	@GetMapping("/account/searchID")
	public void getSearchID() {}
	
	//비밀번호 찾기 화면 (완)
	@GetMapping("/account/tempPassword")
	public void getSearchPassword() {}
	
	//로그아웃 (완)
	@GetMapping("/account/logout")
	public String getLogout(HttpSession session) throws Exception {
		System.out.print("요청옴");
		System.out.print(session);
		session.invalidate();
		return "redirect:/";
	}

	//회원 가입 (선택) 화면 (완)
	@GetMapping("/account/signUp")
	public void getSignup(){}
	
	//회원 가입 화면(학생) (완)
	@GetMapping("/account/signUpStudent")
	public void getSignupStudent() throws Exception { }
	
	//회원 가입 화면(강사)(완)
	@GetMapping("/account/signUpTeacher")
	public void getSignupTeacher() throws Exception { }
	
	//패스워드 변경 화면 (완)
	@GetMapping("/account/changePassword")
	public void getMemberPasswordModify(String userid){
		
	}
	
	//주소검색 (완)
	@GetMapping("/account/searchAddr")
	public void getSearchAddr(@RequestParam(name="addrSearch",required=false) String searchKeyword,
			@RequestParam(name="page", defaultValue="1") int pageNum, Model model) throws Exception {
		
		int postNum = 5;
		int listCount = 10;
		
		PageUtil page = new PageUtil();
		
		Page<AddrEntity> list = service.addrSearch(pageNum, postNum, searchKeyword);
		int totalCount = (int)list.getTotalElements();

		model.addAttribute("list", list);
		model.addAttribute("pageListView", page.getPageAddress(pageNum, postNum, listCount, totalCount, searchKeyword));
		
	}
	
	//학교검색 (완)
	@GetMapping("/account/searchSchool")
	public void getSearchSchool(@RequestParam(name="schoolSearch",required=false) String searchKeyword,
			@RequestParam(name="page", defaultValue="1")int pageNum,Model model) throws Exception {
		
		int postNum = 5;
		int listCount = 10;
		
		PageUtil page = new PageUtil();
		
		Page<SchoolEntity> list = service.schoolSearch(pageNum, postNum, searchKeyword);
		int totalCount = (int)list.getTotalElements();

		model.addAttribute("list", list);
		model.addAttribute("pageListView", page.getSchoolPageAddress(pageNum, postNum, listCount, totalCount, searchKeyword));
		
	}
	
	
	/*POST*/
	//아이디 중복 체크 (완)
	@ResponseBody 
	@PostMapping("/account/idCheck")
	public int getIdCheck(@RequestBody String userid) {
		
		String pw = "12345";
		System.out.println(pwdEncoder.encode(pw));
		return service.idCheck(userid);		
	}
	
	
	//별명 중복 체크 (완)
	@ResponseBody
	@PostMapping("/account/nnCheck")
	public int getNnCheck(@RequestBody String nickName) {		
		return service.nnCheck(nickName);		
	}
	
	
	//이메일 중복 체크 (완)
	@ResponseBody
	@PostMapping("/account/emailCheck")
	public int getEmailCheck(@RequestBody String email) {		
		return service.emailCheck(email);		
	}
	
	
	//로그인 처리 - 실질은 Security에서 처리 (완)
	@PostMapping("/account/login")
	public void postLogin(Model model) {}
	
	
	//로그인 정보 확인 (완)
	@ResponseBody
	@PostMapping("/account/loginCheck")
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
	
	
	//회원 가입 처리(학생) (완)
	@ResponseBody
	@PostMapping("/account/signUpStudent")
	public String postSignupStudent(MemberDTO member) throws Exception {
		member.setPassword(pwdEncoder.encode(member.getPassword()));		
		service.signup(member);		
		return "{\"username\":\"" + URLEncoder.encode(member.getUsername(),"UTF-8") + "\",\"msg\":\"good\"}";
	}
	
	//회원 가입 처리(강사) (완)
	@ResponseBody
	@PostMapping("/account/signUpTeacher")
	public String postSignupTeacher(MemberDTO member, @RequestParam("fileUpload") MultipartFile mpr) throws Exception {
		
		String path = "c:\\Repository\\profile\\"; 
		String org_profilename = "";
		
		if(!mpr.isEmpty()) {
			File targetFile = null; 
				
			org_profilename = mpr.getOriginalFilename();	
			String org_fileExtension = org_profilename.substring(org_profilename.lastIndexOf("."));	
			String stored_profilename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
			
			targetFile = new File(path + stored_profilename);
			mpr.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환
			
				member.setStoredProfilename(stored_profilename);
				
		}
		
		member.setPassword(pwdEncoder.encode(member.getPassword()));
		
		service.signup(member);		
		return "{\"username\":\"" + URLEncoder.encode(member.getUsername(),"UTF-8") + "\",\"msg\":\"good\"}";
		
	}
	
	//아이디찾기 처리 (완)
	@ResponseBody
	@PostMapping("/account/searchID")
	public String postSearchID(MemberDTO member) {
		
		String userid = service.memberSearchID(member);
		
		if(userid.equals("ID_NOT_FOUND"))
			return "{\"msg\":\"ID_NOT_FOUND\"}";
		
		return "{\"msg\":\"good\",\"userid\":\"" + userid + "\"}";
	}
	
	
	//임시 비밀번호 생성 요청 /*이메일 발송처리 구현, 오타 수정 필요*/
	@ResponseBody
	@PostMapping("/account/tempPassword")
	public String postTempPassword(MemberDTO memberDTO) throws Exception{
		
		//아이디 없음
		if(service.idCheck(memberDTO.getUserid()) == 0)
			return "{\"msg\":\"ID_NOT_FOUND\"}";
		
		
		//이름 없음
		if(!service.memberInfo(memberDTO.getUserid()).getUsername()
					.equals(memberDTO.getUsername())) 
			return "{\"msg\":\"USERNAME_NOT_FOUND\"}";
		
		
		//이메일 없음
		if(!service.memberInfo(memberDTO.getUserid()).getEmail()
				.equals(memberDTO.getEmail())) 
		return "{\"msg\":\"EMAIL_NOT_FOUND\"}";
		
		//임시 패스워드 생성
		String rawTempPW = service.tempPassowrdMaker(); /*오타임 tempPassowrdMaker 나중에 한번에 바꿀것*/
		
		//임시패스워드 인코딩 후 수정
		service.memberPasswordModify(memberDTO.getUserid(), rawTempPW);
		
		return "{\"msg\":\"good\",\"password\":\"" + rawTempPW + "\"}";
		
	}
	
	
	//패스워드 변경 처리 (완)
	@PostMapping("/account/changePassword")
	public String postMemberPasswordModify(@RequestParam("old_userpassword") String old_password,
			@RequestParam("new_userpassword") String new_password, HttpSession session) {
		
		String userid = (String)session.getAttribute("userid");
		
		MemberDTO memberDTO = service.memberInfo(userid);
		
		if(pwdEncoder.matches(old_password, memberDTO.getPassword()))
			{service.memberPasswordModify(userid, new_password);
			return "redirect:/account/logout";
			}else {
				return "{\"msg\":\"PASSWORD_NOT_FOUND\"}";
			}
		
	}
	
}
