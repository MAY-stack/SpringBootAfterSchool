package com.afterschool.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afterschool.entity.RegInfoEntity;
public interface RegInfoRepository extends JpaRepository<RegInfoEntity,String>{
	
	//수강 신청한 강좌 중 특정강의 정보
	public RegInfoEntity findByUseridAndLectureSeqno(String userid, String lectureSeqno);
	
	//학생의 한 강좌 중 완강한 강의 개수
	@Query(value="SELECT COUNT (lecture_seqno) FROM tbl_reginfo WHERE userid =:userid and class_code =:classCode and finish_check =:finishCheck", nativeQuery=true)
	public int countFinishedLecture(@Param("userid") String userid, @Param("classCode") String classCode, @Param("finishCheck") String finishCheck);
	
}
