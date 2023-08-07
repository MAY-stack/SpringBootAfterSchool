package com.afterschool.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afterschool.dto.MemberDTO;
import com.afterschool.dto.RegInfoDTO;
import com.afterschool.dto.ReviewDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.LectureEntity;
import com.afterschool.entity.RegInfoEntity;
import com.afterschool.entity.RegisterEntity;
import com.afterschool.entity.ReviewEntity;
import com.afterschool.entity.WishEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.entity.repository.ReviewRepository;
import com.afterschool.entity.repository.WishRepository;
import com.afterschool.service.MemberService;
import com.afterschool.service.StudentService;
import com.afterschool.service.TeacherService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService service;
	private final TeacherService teacherService;
	private final MemberService memberService;
	private final ClassRepository classRepository;
	private final ReviewRepository reviewRepository;
	private final WishRepository wishRepository;
	
	//강의실 화면
	//*****변경*****//
	@GetMapping("/student/myClass")
	public void getMyClass(@RequestParam("userid") String userid, Model model)throws Exception{

		//해당 학생의 수강 정보
		/* list1 : 수강중인 강좌
		 * list2 : 수강완료한 강좌
		 * wishlist: 찜하기 체크되어 있는 강좌*/
		
		//수강중인 강좌
		List<RegisterEntity> takingClassList = service.getRegisterClassListByFinishCheck(userid, "N").get();
		//수강 완료 강좌
		List<RegisterEntity> finishedClassList = service.getRegisterClassListByFinishCheck(userid, "Y").get();
		//찜하기 강좌 리스트
		List<WishEntity> wishClassList = wishRepository.findAllByUserid(userid);
		//내가 쓴 리뷰 리스트 (0619 민경 추가)
		List<ReviewEntity> reviewList = reviewRepository.findAllByUserid(userid);
		
		model.addAttribute("list1", takingClassList);
		model.addAttribute("list2", finishedClassList);
		model.addAttribute("wishlist", wishClassList);
		
		//0619 리뷰 리스트 (0619 민경 추가)
		model.addAttribute("review", reviewList);	
		
	}
	
	//회원 정보 수정 보기 (완)
	@GetMapping("/student/myInfoModify")
	public void getMyInfoModify(@RequestParam("userid") String userid, Model model, HttpSession session)throws Exception {
		
		//해당 학생의 회원 정보
		MemberDTO memberDTO = memberService.memberInfo(userid);
		model.addAttribute("member", memberDTO);
		//회원 정보 수정 후 세션의 아바타, 닉네임 변경
		session.setAttribute("avatar", memberDTO.getAvatar());
		session.setAttribute("nickname", memberDTO.getNickname());
		
	}
	
	//강의 플레이어 보기
	@GetMapping("/student/classPlayer")
	public void getClassPlayer(String userid, String lectureSeqno, String classCode, Model model) throws Exception {
		
		//강좌의 전체 강의 목록		
		List<LectureEntity> lectureList = service.lectureList(classCode);
		model.addAttribute("lecturelist", lectureList);
		
		//현재 강의 정보
		//강좌 코드의 강좌 정보
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		model.addAttribute("class",classEntity);
		RegisterEntity registerEntity = service.currentlecture(userid, classCode).get();
		RegInfoEntity reginfoEntity = service.currentRegInfo(userid, lectureSeqno);
		
		model.addAttribute("lectureInfo", registerEntity);
		model.addAttribute("regInfo",reginfoEntity);		

	}
	
	//수강 신청
	@ResponseBody
	@PostMapping("/student/registerClass")
	public String postRegister(@RequestBody Map<String, String> map, HttpSession session) {
				
		//접속 사용자 아이디
		String userid = (String) session.getAttribute("userid");
		
		System.out.println(map);
		
		service.registerClass(userid, map.get("classCode"), map.get("className"));
		service.createReginfo(userid, map.get("classCode"));
		return "{\"msg\":\"good\"}";
	}
	
	
	//리뷰 등록
	@ResponseBody
	@PostMapping("/student/classReview")
	public String postClassReview(ReviewDTO reviewDTO) {
		
		//리뷰에 강좌명 넣어 주기
		ClassEntity classEntity = classRepository.findByClassCode(reviewDTO.getClassCode());
		reviewDTO.setClassName(classEntity.getClassName());
		
		//리뷰 등록
		/*rate, userid, classCode, reviewContent*/
		reviewDTO.setReviewDate(LocalDateTime.now());
		reviewRepository.save(reviewDTO.dtoToEntity(reviewDTO));
		
		//리뷰 등록 여부 체크 (6/19 민경 수정)
		service.regReviewCheck(reviewDTO);
		
		//강좌 평점 업데이트 (6/19 민경 수정)		
		teacherService.updateAvgRate(reviewDTO.getClassCode());
		
		return "{\"msg\":\"good\"}";
	}
	

	
	//강의 시청 시간 저장 (6/19 오후 민경 필요부분 추가 및 수정)
	@ResponseBody
	@PostMapping("/student/classPlayer")
	public void postClassPlayer(@RequestBody RegInfoDTO reginfoDTO) {
		//강의 시청 시간 저장
		service.saveTime(reginfoDTO);
		
		//수강테이블 마지막 수강 강의 번호 업데이트
		service.updateLectureSeq(reginfoDTO);
		
		//수강률 수정
		service.updateClassFinishedPercent(reginfoDTO);
		
	}
	
	
	//회원 정보 수정 처리 (완)
	@PostMapping("/student/myInfoModify")
	public String postMyInfoModify(MemberDTO memberDTO)throws Exception {
		
		String userid = memberDTO.getUserid();
		String email = memberDTO.getEmail();
		String school = memberDTO.getSchool();
		String nickname = memberDTO.getNickname();
		String telno = memberDTO.getTelno();
		String zipcode = memberDTO.getZipcode();
		String address = memberDTO.getAddress();
		String avatar = memberDTO.getAvatar();
		
		service.studentInfoUpdate(userid, email, school, nickname, telno, zipcode, address, avatar);

		return "redirect:/student/myInfoModify?userid="+userid;
	}
	
	//찜하기
	@ResponseBody
	@PostMapping("/student/wishClass")
	public String postWishClass(@RequestBody Map<String, Object> wishCheckData, HttpSession session, Model model) {
		
		String wishCheck = (String)wishCheckData.get("wishCheck");		
		String classCode = (String)wishCheckData.get("classCode");		
		String className = (String)wishCheckData.get("className");		
		String sessionUserId = (String) session.getAttribute("userid");
		
		if(wishCheck.equals("N")) {
			service.deleteWish(classCode, sessionUserId);
		} else if(wishCheck.equals("Y")) {
			service.wishClass(sessionUserId, classCode, className);
		}
		
		return "{\"msg\":\"good\"}";
	}
	
}
