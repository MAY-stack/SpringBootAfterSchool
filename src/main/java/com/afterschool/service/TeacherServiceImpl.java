package com.afterschool.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.afterschool.dto.ClassDTO;
import com.afterschool.dto.LectureDTO;
import com.afterschool.dto.MemberDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.entity.repository.LectureRepository;
import com.afterschool.entity.repository.MemberRepository;
import com.afterschool.entity.repository.RegisterRepository;
import com.afterschool.entity.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{

	private final MemberRepository memberRepository;
	private final ClassRepository classRepository;
	private final LectureRepository lectureRepository;
	private final RegisterRepository registerRepository;
	private final ReviewRepository reviewRepository;
	
	
	//강사 목록
	@Override
	public List<MemberEntity> teacherlist(String role){
		return memberRepository.findAllByRole(role);
	};
	
	//강사 정보
	@Override
	public MemberDTO teacherInfo(String userid) {
		// TODO Auto-generated method stub
		return null;
	};
	
	//강사의 강좌 목록 보기
	@Override
	public List<ClassEntity> classlist(String userid){
		return classRepository.findAllByUserid(userid);
	}
	
	//강좌명 중복 확인
	@Override
	public int classNameCheck(String className) {
		return classRepository.findByClassName(className).isEmpty()?0:1;
	};
	
	
	//강좌 등록 신청 등록
	@Override
	public void regClassApply(ClassDTO classDTO) {
		classDTO.setClassUploadDate(LocalDateTime.now());
		classRepository.save(classDTO.dtoToEntity(classDTO));
		
	}
	
	//강좌 명으로 classCode 찾기
	@Override
	public String getClassCodeForApply(String className) {		
		return classRepository.findByClassName(className).get().getClassCode(); 
	}

	//강사 회원 정보 수정
	@Override
	//강사 회원 정보 수정
	public void teacherInfoUpdate(String userid, String email, String company, String telno, 
	String zipcode, String address, String introduceLine, String introduce, String stored_profilename) throws Exception{
		MemberEntity memberEntity = memberRepository.findById(userid).get();
		memberEntity.setEmail(email);
		memberEntity.setCompany(company);
		memberEntity.setTelno(telno);
		memberEntity.setZipcode(zipcode);
		memberEntity.setAddress(address);
		memberEntity.setIntroduceLine(introduceLine);
		memberEntity.setIntroduce(introduce);
		memberEntity.setStoredProfilename(stored_profilename);
		
		memberRepository.save(memberEntity);
	};
	
	//등록 신청한 강좌의 강의 정보 등록
	@Override
	public void regClassApplyLectureInfo(LectureDTO lectureDTO) {
		lectureRepository.save(lectureDTO.dtoToEntity(lectureDTO));	
	};

	//강좌의 완강률 업데이트(6/19 민경 추가)
	@Override
	public void upadteFinishedStudentRate(ClassEntity classEntity) {
		
		//완강한 학생 수
		int finishedStudentCnt = registerRepository.finishedStudentCnt(classEntity.getClassCode(), "Y");
		System.out.println(finishedStudentCnt);
		
		if(finishedStudentCnt != 0) { //완강 학생 수가 1명 이상이면
						
			//강좌의 수강신청 학생 수
			int registerdStudentCnt = classEntity.getRegisteredStudentCnt();
			
			//완강률 계산
			double finishedStudentRate = Math.round((double)finishedStudentCnt/(double)registerdStudentCnt*100);

			System.out.println(registerdStudentCnt);
			System.out.println(finishedStudentRate);
			
			
			//현재 강좌의 완강률 set
			classEntity.setFinishedRate(finishedStudentRate);

			//완강률 정보 저장
			classRepository.save(classEntity);
			
		}
		
	}
	
	//강좌 평점 업데이트(6/19 민경 추가)
	public void updateAvgRate(String classCode) {
		
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		double newAvg = reviewRepository.getClassAvg(classCode);
		classEntity.setAvgRate(newAvg);
		classRepository.save(classEntity);
	}
}
