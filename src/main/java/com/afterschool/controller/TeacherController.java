package com.afterschool.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.afterschool.category.entity.CategoryAEntity;
import com.afterschool.category.entity.CategoryBEntity;
import com.afterschool.category.entity.CategoryCEntity;
import com.afterschool.category.entity.CategoryDEntity;
import com.afterschool.category.service.CategoryService;
import com.afterschool.dto.ClassDTO;
import com.afterschool.dto.LectureDTO;
import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.service.MemberService;
import com.afterschool.service.TeacherService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TeacherController {
	
	private final ClassRepository classRepository;
	private final TeacherService service;
	private final MemberService memberService;
	private final CategoryService categoryService;

	//(강사)강의실 화면
	@GetMapping("/teacher/teacherMyClass")
	public void getTeacherMyClass(@RequestParam(name="userid", required = false) String userid, HttpSession session, Model model)throws Exception{
		
		if(userid.equals(null)) {
			userid = (String) session.getAttribute("userid");
		}
		
		/*
		 * list1 : 승인된 강좌 목록
		 * List<ClassEntity>
		 * list2: 승인 대기 목록
		 * List<ClassEntity>
		 * list3: 승인 거절 강좌 목록
		 * List<ClassEntity>
		 * */
		
		//해당 강사 승인 강좌 목록
		List<ClassEntity> classList = classRepository.findAllByUseridAndApproveStatus(userid, "A");
		model.addAttribute("list1", classList);
		
		//승인 대기 목록
		List<ClassEntity> approvedClassList = classRepository.findAllByUseridAndApproveStatus(userid, "W");
		model.addAttribute("list2", approvedClassList);
		
		//승인 거절 강좌 목록
		List<ClassEntity> refusedClassList = classRepository.findAllByUseridAndApproveStatus(userid, "R");
		model.addAttribute("list3", refusedClassList);

	}
	
	//강좌 등록 화면 보기 (완)
	@GetMapping("/teacher/classUpload")
	public void getClassUpload(){
		
	}
	
	//강사 회원 정보 수정 화면	(6/19 민경 수정- 완)
	@GetMapping("/teacher/teacherMyInfoModify")
	public void getMyInfoModify(@RequestParam(name="userid", required = false) String userid, HttpSession session, Model model)throws Exception {
		
		if(userid.equals(null)) {
			userid = (String) session.getAttribute("userid");
		}
		
		//해당 강사의 회원 정보
		MemberDTO memberDTO = memberService.memberInfo(userid);
		model.addAttribute("member", memberDTO);
		//변경된 프로필 이미지 정보 session에 set
		session.setAttribute("profile", memberDTO.getStoredProfilename());
		
	}
	
	/*POST*/
	//강좌명 중복 확인 (완)
	@ResponseBody
	@PostMapping("/teacher/classNameCheck")
	public int postClassNameCheck(@RequestBody String className) {		
		return service.classNameCheck(className);		
	}
	
	//강좌 등록 처리 (완)
	@ResponseBody
	@PostMapping("/teacher/classUpload")
	public String postClassUpload(ClassDTO classDTO, @RequestParam("classImage") MultipartFile classImage,
								HttpSession session, @RequestParam("LectureTitleList") List<String> lectureTitleList,
								@RequestParam("VideoFileList") List<MultipartFile> multipartFile) throws IllegalStateException, IOException{
		
		System.out.println("강좌 등록 시작");

		//세션의 아이디를 강좌의 강사 아이디로 설정
		String userid = (String) session.getAttribute("userid");
		classDTO.setUserid(userid);	
		//세션의 이름을 강좌의 강사 이름으로 설정 /*PK 설정 후 변경하기*/
		String username = (String) session.getAttribute("username");
		classDTO.setUsername(username);
		
		//카테고리
		String category = classDTO.getCategory();
		//강좌명
		String className = classDTO.getClassName();

		
		//카테고리에 맞는 강좌 등록 및 SEQ 생성
		if(category.equals("A")){
			CategoryAEntity categoryAEntity = new CategoryAEntity();
			categoryAEntity.setClassName(className);
			categoryService.applyA(categoryAEntity);
		}
		if(category.equals("B")){
			CategoryBEntity categoryBEntity = new CategoryBEntity();
			categoryBEntity.setClassName(className);
			categoryService.applyB(categoryBEntity);
		}
		if(category.equals("C")){
			CategoryCEntity categoryCEntity = new CategoryCEntity();
			categoryCEntity.setClassName(className);
			categoryService.applyC(categoryCEntity);
		}
		if(category.equals("D")){
			CategoryDEntity categoryDEntity = new CategoryDEntity();
			categoryDEntity.setClassName(className);
			categoryService.applyD(categoryDEntity);
		}		
		
		//이미지, 비디오 파일 처리
		File targetFile = null;
		String path = "";
		
		//강좌 대표 이미지 서버에 저장 및 storedClassProfilename 값 set
		path = "c:\\Repository\\class\\classImage\\";
		targetFile = new File(path);
		//파일 저장 경로 없을 시 생성
		if(!targetFile.exists()) targetFile.mkdirs();	
		
		String org_classProfileName = classImage.getOriginalFilename();	
		String org_ImageExtension = org_classProfileName.substring(org_classProfileName.lastIndexOf("."));	
		String stored_classProfileName = UUID.randomUUID().toString().replaceAll("-", "") + org_ImageExtension;	
		
		System.out.printf("%s, %s\n", org_ImageExtension, stored_classProfileName);
		
		targetFile = new File(path + stored_classProfileName);
		classImage.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환

		//강좌 대표이미지 저장 주소 값 넣기
		classDTO.setStoredClassProfilename(stored_classProfileName);
		
		//등록 신청 한 강좌의 강좌 코드 받아강좌코드, 강의 식별번호 생성 및 set
		String classCode = categoryService.genClassCode(category, className);
		classDTO.setClassCode(classCode);
		
		System.out.println(classCode);
		
		//강좌 등록
		service.regClassApply(classDTO);

		
		//강의 정보 등록
		//강좌 명으로 강좌 코드(코드 생성전까지 개발중에는 classSeq) get	
		if(!multipartFile.isEmpty()) { //강의 영상 파일 서버에 저장
			path = "c:\\Repository\\class\\lectureVideos\\";
			targetFile = new File(path);
			//파일 저장 경로 없을 시 생성			
			if(!targetFile.exists()) targetFile.mkdirs();
			//저장할 강의 객체 생성
			LectureDTO lectureDTO = new LectureDTO();
			
			
			//강의 객체의 classCode set
			lectureDTO.setClassCode(classCode);
			for(int i = 0; i<multipartFile.size(); i++) {
				
				//강의 코드 생성 및 set
				String lectureSeqno = classCode+"_"+String.format("%02d", i+1);
				lectureDTO.setLectureSeqno(lectureSeqno);

				lectureDTO.setClassName(className);
								
				String org_videoname = multipartFile.get(i).getOriginalFilename();	
				String org_videoExtension = org_videoname.substring(org_videoname.lastIndexOf("."));	
				String stored_videoname = lectureSeqno + org_videoExtension;
				
				System.out.printf("%s, %s\n", org_videoname, stored_videoname);
				targetFile = new File(path + stored_videoname);
				multipartFile.get(i).transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환

				//강의 제목, 강의 영상 저장명 set
				lectureDTO.setLectureTitle(lectureTitleList.get(i));
				lectureDTO.setStoredVideoname(stored_videoname);

				service.regClassApplyLectureInfo(lectureDTO);
			}
			service.regClassApplyLectureInfo(lectureDTO);
		}
		return "{\"msg\":\"good\",\"userid\":\"" + userid + "\"}";
	}

	//강사 회원 정보 수정 처리
	@PostMapping("/teacher/teacherMyInfoModify")
	public String postInfoModify(MemberDTO memberDTO, @RequestParam("fileUpload") MultipartFile mpr)throws Exception {
		
		//수정 정보 뽑기
		String userid = memberDTO.getUserid();
		String email = memberDTO.getEmail();
		String company = memberDTO.getCompany();
		String telno = memberDTO.getTelno();
		String zipcode = memberDTO.getZipcode();
		String address = memberDTO.getAddress();
		String introduceLine = memberDTO.getIntroduceLine();
		String introduce = memberDTO.getIntroduce();
		
		//프로필 이미지 수정
		String path = "c:\\Repository\\profile\\"; 
		String org_profilename = "";
		String stored_profilename ="";
		if(!mpr.isEmpty()) {
			File targetFile = null; 
				
			org_profilename = mpr.getOriginalFilename();	
			String org_fileExtension = org_profilename.substring(org_profilename.lastIndexOf("."));	
			stored_profilename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
			
			targetFile = new File(path + stored_profilename);
			mpr.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환
		}
		
		service.teacherInfoUpdate(userid, email, company, telno, zipcode, address, introduceLine, introduce, stored_profilename );

		return "redirect:/teacher/teacherMyInfoModify?userid="+userid;
	}
	
}
