package com.afterschool.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.afterschool.dto.MemberDTO;
import com.afterschool.dto.RegInfoDTO;
import com.afterschool.dto.RegisterDTO;
import com.afterschool.dto.ReviewDTO;
import com.afterschool.dto.WishDTO;
import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.LectureEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.RegInfoEntity;
import com.afterschool.entity.RegisterEntity;
import com.afterschool.entity.WishEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.entity.repository.LectureRepository;
import com.afterschool.entity.repository.MemberRepository;
import com.afterschool.entity.repository.RegInfoRepository;
import com.afterschool.entity.repository.RegisterRepository;
import com.afterschool.entity.repository.WishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

	private final MemberRepository memberRepository;
	private final RegisterRepository registerRepository;
	private final RegInfoRepository reginfoRepository;
	private final LectureRepository lectureRepository;
	private final WishRepository wishRepository;
	private final ClassRepository classRepository;
	private final TeacherService teacherService;
	
	
	//수강신청 여부 정보
	@Override
	public int registerCheck(String userid, String classCode) {
		return registerRepository.findByUseridAndClassCode(userid, classCode).isEmpty()?0:1;
	}

	//강좌 찜하기
	@Override
	public void wishClass(String userid, String classCode, String className) {
		WishDTO wishdto = new WishDTO();
		wishdto.setClassCode(classCode);
		wishdto.setUserid(userid);
		wishdto.setClassName(className);
		wishdto.setWishdate(LocalDateTime.now());
		wishRepository.save(wishdto.dtoToEntity(wishdto));
	}
	
	//강좌 찜하기 정보 가져오기
	@Override
	public int wishCheckView(String classCode, String userid) {
		return wishRepository.findByClassCodeAndUserid(classCode, userid).isEmpty()?0:1;
	}
	
	//찜하기 취소
	@Override
	public void deleteWish(String classCode, String userid) {
		WishEntity wishEntity = wishRepository.findByClassCodeAndUserid(classCode, userid).get();
		wishRepository.delete(wishEntity);
	}

	
	// student/myInfo-회원 정보 가져오기
	@Override
	public MemberDTO studentInfo(String userid) throws Exception{
		return memberRepository.findById(userid).map(member-> new MemberDTO(member)).get();
	}
	
	// 정보 수정하기
	@Override
	public void studentInfoUpdate(String userid, String email, String school, String nickname, String telno,
		String zipcode, String address, String avatar) throws Exception{
		
		MemberEntity memberEntity = memberRepository.findById(userid).get();
					memberEntity.setEmail(email);
					memberEntity.setSchool(school);
					memberEntity.setNickname(nickname);
					memberEntity.setTelno(telno);
					memberEntity.setZipcode(zipcode);
					memberEntity.setAddress(address);
					memberEntity.setAvatar(avatar);
		memberRepository.save(memberEntity);
		
	};
	
	
	//수강 신청 (6/19 12:00 민경 수정)
	@Override
	public void registerClass(String userid, String classCode, String className) {
			
		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setClassCode(classCode);
		registerDTO.setUserid(userid);
		registerDTO.setLectureSeqno(classCode+"_01");
		registerDTO.setClassName(className);
		registerDTO.setPlaybar(0);
		registerDTO.setRecentLogindate(LocalDateTime.now());
		registerDTO.setRegdate(LocalDateTime.now());
		registerDTO.setPercent((long) 0);
		registerRepository.save(registerDTO.dtoToEntity(registerDTO));
		
		//강좌의 수강 신청 학생 수 업데이트
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		classEntity.setRegisteredStudentCnt(registerRepository.regStudentCnt(classCode));
		classRepository.save(classEntity);
	}
	
	//수강 신청한 강좌 수강 현황(regInfo) 만들기
	@Override
	public void createReginfo(String userid, String classCode) {

		//강좌 코드의 강좌 정보
		
		List<String> lectureseqnoList = lectureRepository.findAllByClassCode(classCode).stream().map(seqno->seqno.getLectureSeqno()).collect(Collectors.toList());
		
		RegInfoDTO reginfoDTO = new RegInfoDTO();
		for(String seqno:lectureseqnoList) {
			reginfoDTO.setUserid(userid);
			reginfoDTO.setLectureSeqno(seqno);
			reginfoDTO.setClassCode(classCode);
			reginfoDTO.setFinishCheck("N");
			reginfoRepository.save(reginfoDTO.dtoToEntity(reginfoDTO));
		}
		
	}
	
	//학생의 강좌 수강신청 정보 가져오기
	@Override
	public List<RegisterEntity> registerInfo(String userid) throws Exception{
		return registerRepository.findAllByUserid(userid);
	}
	

	//완강 여부에 따른 강좌 리스트
	@Override
	public Optional<List<RegisterEntity>> getRegisterClassListByFinishCheck(String userid, String classFinishCheck){
		return registerRepository.findAllByUseridAndClassFinishCheck(userid, classFinishCheck);	
	}
	
	//리뷰 등록 여부 체크 (6/19민경 추가)
	@Override
	public void regReviewCheck(ReviewDTO reviewDTO) {
		Optional<RegisterEntity> registerEntity = registerRepository.findByUseridAndClassCode(reviewDTO.getUserid(), reviewDTO.getClassCode());
		RegisterEntity register = registerEntity.get();
		register.setReviewCheck("Y");		
		registerRepository.save(register);
	}
	
	// student 수강중인 강좌의 현재 수강 강의 정보 가져오기
	@Override
	public Optional<RegisterEntity> currentlecture(String userid, String classCode) throws Exception{
		return registerRepository.findByUseridAndClassCode(userid, classCode);
	}
	
	// 강좌의 강의 목록 가져오기
	@Override
	public List<LectureEntity> lectureList(String classCode) throws Exception{
		return lectureRepository.findAllByClassCode(classCode);
	}
	
	// 현재 들으려는 강의 정보 가져오기
	@Override
	public RegInfoEntity currentRegInfo(String userid, String lectureSeqno){
		return reginfoRepository.findByUseridAndLectureSeqno(userid, lectureSeqno);
	}
	
	// 시청한 기록 저장
	@Override
	public void saveTime(RegInfoDTO regInfoDTO) {
		RegInfoEntity regInfoEntity = reginfoRepository.findByUseridAndLectureSeqno(regInfoDTO.getUserid(), regInfoDTO.getLectureSeqno());
		regInfoEntity.setPlaybar(regInfoDTO.getPlaybar());
		regInfoEntity.setPlaytime(regInfoDTO.getPlaytime());
		regInfoEntity.setFinishCheck(regInfoDTO.getFinishCheck());
		regInfoEntity.setClassCode(regInfoDTO.getClassCode());

		reginfoRepository.save(regInfoDTO.dtoToEntity(regInfoDTO));
	}
	
	//수강테이블 마지막 강의 번호 변경 (6/19민경 추가)
	@Override
	public void updateLectureSeq(RegInfoDTO reginfoDTO) {
		
		String userid = reginfoDTO.getUserid();
		String classCode = reginfoDTO.getClassCode();
		String lectureSeqno = reginfoDTO.getLectureSeqno();
		
		RegisterEntity registerEntity = registerRepository.findByUseridAndClassCode(userid, classCode).get();
		registerEntity.setLectureSeqno(lectureSeqno);
		registerRepository.save(registerEntity);
		
	}
	
	
	//강좌의 수강률 저장 (6/19 오후 민경 추가)
	@Override
	public void updateClassFinishedPercent(RegInfoDTO reginfoDTO) {
		String userid = reginfoDTO.getUserid();
		String classCode = reginfoDTO.getClassCode();
		
		//현 강좌의 수강 완료 강의 수
		int finishedCnt = reginfoRepository.countFinishedLecture(userid, classCode, "Y");
		
		if(finishedCnt != 0) { //다 들은 강좌가 1개 이상이면

			//현강좌에 대한 수강 정보
			RegisterEntity registerEntity = registerRepository.findByUseridAndClassCode(userid, classCode).get();
			
			//현 강좌의 전체 강의 수
			int lectureCnt = classRepository.findByClassCode(classCode).getLectureCount();

			
			//수강률 계산
			float percent = (float)finishedCnt/(float)lectureCnt*100;
			System.out.println(finishedCnt);
			System.out.println(lectureCnt);
			System.out.println(percent);
			
			//현재 강좌의 수강율 set
			registerEntity.setPercent(percent);
			
			//수강률 100%이면 완강여부 "Y"로 set
			if(percent == 100.0) {
				registerEntity.setClassFinishCheck("Y");
				//강좌의 학생 완강률 업데이트
				ClassEntity classEntity = classRepository.findByClassCode(classCode);
				teacherService.upadteFinishedStudentRate(classEntity);
			}
			
			//수강율 및 완강 정보 저장
			registerRepository.save(registerEntity);
			
		}
	}

}
