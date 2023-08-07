package com.afterschool.service;

import java.util.List;

import com.afterschool.dto.ClassDTO;
import com.afterschool.dto.LectureDTO;
import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.MemberEntity;

public interface TeacherService {

	//강사 목록
	public List<MemberEntity> teacherlist(String role);
	
	//강사의 회원 정보
	public MemberDTO teacherInfo(String userid);
	
	//강사 회원 정보 수정
	public void teacherInfoUpdate(String userid, String email, String company, String telno, 
	String zipcode, String address, String introduceLine, String introduce, String stored_profilename) throws Exception;
	
	//강사의 강좌 목록
	public List<ClassEntity> classlist(String userid);

	//강좌명 중복 확인
	public int classNameCheck(String className);
	
	
	//강좌 등록 신청
	public void regClassApply(ClassDTO classDTO);
	
	//강좌 명으로 강좌 코드 찾기
	public String getClassCodeForApply(String className);
	
	//등록 신청한 강좌의 강의 정보 등록
	public void regClassApplyLectureInfo(LectureDTO lectureDTO);
	
	//강좌의 완강률 업데이트(6/19 민경 추가)
	public void upadteFinishedStudentRate(ClassEntity classEntity);
	
	//강좌 평점 업데이트(6/19 민경 추가)
	public void updateAvgRate(String classCode);
}
