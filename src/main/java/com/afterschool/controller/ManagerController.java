package com.afterschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.service.MasterService;
import com.afterschool.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ManagerController {
	
	private final MemberService service;
	private final MasterService masterservice;
	private final ClassRepository classRepository;
	
	/*GET*/
	//등록 신청 강좌 보기 화면
	@GetMapping("/manager/classUploadCheckView")
	public void getClassUploadCheckView(@RequestParam("classCode") String classCode, Model model){

		ClassEntity view= masterservice.view(classCode);	
		model.addAttribute("class", view);
		model.addAttribute("classCode", classCode);
		
	}
	
	//등록 신청 강좌 보기 화면
	@GetMapping("/manager/classUploadCheck")
	public void getClassUploadCheck(){}
	
	//회원 정보 보기
	@GetMapping("/manager/memberInfo")
	public void getMemberInfo(Model model){
		String role = "MANAGER";
		model.addAttribute("member", service.getMember(role));
	}
	
	//회원 탈퇴
	@ResponseBody
	@PostMapping("/manager/memberInfo")
	public String postMemberDelete(@RequestBody String userid) throws Exception {
		System.out.println("userid is " + userid);
		service.memberInfoDelete(userid);
		
		return "{\"message\":\"good\"}";
	}
	
	//강좌 승인 요청 목록
	@GetMapping("/manager/classUploadCheckList") 
	public void getClassUploadCheckList(Model model) {
		
		//(6/19 민경 수정)
		model.addAttribute("list", classRepository.findAllByApproveStatus("W"));
	}
	
	//강좌 등록 승인
	@GetMapping("/manager/classApprove")
	public String postClassApprove(@RequestParam("classCode") String classCode) throws Exception {
		//영상 처리 관련(6/19 민경 삭제)
		
		//강좌의 승인 대기 상태 변경 처리 "A"
		masterservice.approve(classCode);
		
		return "redirect:/manager/classUploadCheckList";
	}
	
	//강좌 등록 거부
	@GetMapping("/manager/classRefuse")
	public String postClassRefuse(@RequestParam("classCode") String classCode) {
		
		//강좌의 승인 대기 상태 변경 "R"
		masterservice.refuse(classCode);
		
		return "redirect:/manager/classUploadCheckList";
	}
	
}
