package com.afterschool.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.LectureEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.ReviewEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.entity.repository.LectureRepository;
import com.afterschool.entity.repository.MemberRepository;
import com.afterschool.entity.repository.ReviewRepository;
import com.afterschool.service.MemberService;
import com.afterschool.service.StudentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {
	
	private final StudentService studentService;
	private final ClassRepository classRepository;
	private final LectureRepository lectureRepository;
	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	//사이트 전체 강좌 목록, 카테고리별로 보기//
	@GetMapping("/search/classList")
	public void getClassList(@RequestParam(name="category", defaultValue="", required=false) String category,
								@RequestParam(name="keyword", defaultValue="", required=false) String keyword,
								Model model) throws Exception {
		
		//검색, 카테고리 해당 강좌 목록 (승인된 강좌만 보이게 수정 - 완 )
		if(category.equals("") && keyword.equals("")) {
			List<ClassEntity> classList = classRepository.findAllByApproveStatus("A");
			model.addAttribute("list", classList);
		}else if(!category.equals("") && keyword.equals("")) {
			List<ClassEntity> classList = classRepository.findAllByCategoryAndApproveStatus(category, "A");
			model.addAttribute("list", classList);
		}else if(category.equals("") && !keyword.equals("")) {
			List<ClassEntity> classList = classRepository.findAllByClassNameContainingAndApproveStatus(keyword,"A");
			model.addAttribute("list", classList);
		}else {
			List<ClassEntity> classList = classRepository.findAllByCategoryContainingOrClassNameContainingAndApproveStatus(category, keyword, "A");
			model.addAttribute("list", classList);
		}
		
	}
	
	//해당 강좌 정보 상세 보기
	@GetMapping("/search/classInfo")
	public void getClassView(@RequestParam("classCode") String classCode, Model model, HttpSession session) throws Exception {
		//강좌 상세 정보
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		model.addAttribute("class", classEntity);
		
		//접속자의 userid
		String userid = (String)session.getAttribute("userid");		
		
		//강좌의 강의 리스트
		List<LectureEntity> lectureList = lectureRepository.findAllByClassCode(classCode);
		model.addAttribute("lecturelist", lectureList);
		
		//강좌의 리뷰 리스트
		List<ReviewEntity> reviewList = reviewRepository.findAllByClassCode(classCode);
		model.addAttribute("list", reviewList);
		//System.out.println("review is " + reviewList.get(0).getClassCode());
		for (int i=0;i<reviewList.size();i++) {
			System.out.println("review is " + reviewList.get(i).getClassCode());
		}
		
		//찜하기 정보 (찜하기 정보 있으면 1, 없으면 0)
		int wishView = studentService.wishCheckView(classCode, userid);
		if(wishView == 0) {
			model.addAttribute("wishCheck", "N");
		} else if(wishView == 1) {
			model.addAttribute("wishCheck", "Y");
		}
		model.addAttribute("wishCheckView", wishView);
		
		//접속자의 수강신청 정보 있으면 1 없으면 0		
		int registerCheck = studentService.registerCheck(userid, classCode);	
		model.addAttribute("registerCheck", registerCheck);	
		
	}
	
	
	/*강사*/	
	//강사 목록
	@GetMapping("/search/teacherList")
	public void getTeacherList(@RequestParam(name="category",defaultValue="",required=false) String category, 
								@RequestParam(name="keyword", defaultValue="", required=false) String keyword,
								Model model) throws Exception {
		
		//검색, 카테고리 해당 강사 목록 }(6/19 list->member 수정- 완)
		if(category.equals("") && keyword.equals("")) {
			List<MemberEntity> teacherList = memberRepository.findAllByRole("TEACHER");
			model.addAttribute("member", teacherList);
		}else if(!category.equals("") && keyword.equals("")) {
			List<MemberEntity> teacherList = memberRepository.findAllByRoleAndMainSubject("TEACHER", category);
			model.addAttribute("member", teacherList);
		}else if(category.equals("") && !keyword.equals("")) {
			List<MemberEntity> teacherList = memberRepository.findAllByRoleAndUsernameContaining("TEACHER", keyword);
			model.addAttribute("member", teacherList);
		}else {
			List<MemberEntity> teacherList = memberRepository.findAllByUsernameContainingAndRoleAndMainSubject(keyword, "TEACHER", category);
			model.addAttribute("member", teacherList);
		}
		
	}
	
	//강사 정보 상세보기
	@GetMapping("/search/teacherInfo")
	public void getTeacherInfo(@RequestParam("userid") String userid, Model model) throws Exception {
		//강사의 정보
		MemberDTO memberDTO = memberService.memberInfo(userid); // 변경
		model.addAttribute("teacher", memberDTO);
		
		//강사의 강좌 목록 (6/19 승인된 강좌만 보이게 수정 - 완)
		List<ClassEntity> classList = classRepository.findAllByUseridAndApproveStatus(userid, "A");
		model.addAttribute("classlist", classList);
		
	}
	
}
