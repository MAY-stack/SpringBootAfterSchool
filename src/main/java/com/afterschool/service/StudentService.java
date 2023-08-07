package com.afterschool.service;

import java.util.List;
import java.util.Optional;

import com.afterschool.dto.MemberDTO;
import com.afterschool.dto.RegInfoDTO;
import com.afterschool.dto.ReviewDTO;
import com.afterschool.entity.LectureEntity;
import com.afterschool.entity.RegInfoEntity;
import com.afterschool.entity.RegisterEntity;

public interface StudentService {
	
	//수강 및 찜하기 정보
	public int registerCheck(String userid, String classCode);
	
	// student/myInfo-회원 정보 가져오기
	public MemberDTO studentInfo(String userid) throws Exception;
	
	// 정보 수정하기
	public void studentInfoUpdate(String userid, String email, String school, String nickname, String telno,
	String zipcode, String address, String avatar) throws Exception;
	
	//강좌 찜하기
	public void wishClass(String userid, String classCode, String className);
	
	//찜하기 정보 가져오기
	public int wishCheckView(String classCode, String userid);
	
	//찜하기 취소
	public void deleteWish(String classCode, String userid);
	
	//수강 신청 하기
	public void registerClass(String userid, String classCode, String className);
	
	//수강 신청한 강좌 강의 현황 만들기
	public void createReginfo(String userid, String classCode);
	
	//student/myInfo- 수강 정보 가져오기
	public List<RegisterEntity> registerInfo(String userid) throws Exception;
	
	//student 수강중인 강좌의 현재 수강 강의 정보 가져오기
	public Optional<RegisterEntity> currentlecture(String userid, String ClassCode) throws Exception;
	
	//완강 여부에 따른 강좌 리스트
	public Optional<List<RegisterEntity>> getRegisterClassListByFinishCheck(String userid, String classFinishCheck);
	
	//리뷰 등록 체크하기 (6/19민경 추가)
	public void regReviewCheck(ReviewDTO reviewDTO);
	
	//강좌의 강의 목록 가져오기
	public List<LectureEntity> lectureList(String Classcode) throws Exception;

	//현재 들으려는 강의 정보 가져오기
	public RegInfoEntity currentRegInfo(String userid, String lectureSeqno);
	
	//시청한 기록 저장
	public void saveTime(RegInfoDTO regInfoDTO);
	
	//수강 테이블의 마지막 수강한 강의 번호 업데이트	(6/19민경 추가)
	public void updateLectureSeq(RegInfoDTO reginfoDTO);
	
	//---6/19 오후 민경 추가---//
	//강좌의 수강률 저장
	public void updateClassFinishedPercent(RegInfoDTO reginfoDTO);


}


