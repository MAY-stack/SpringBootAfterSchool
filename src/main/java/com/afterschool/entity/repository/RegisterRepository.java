package com.afterschool.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afterschool.entity.RegisterEntity;
public interface RegisterRepository extends JpaRepository<RegisterEntity,Long>{

	//학생의 전체 수강 강좌 리스트
	public List<RegisterEntity> findAllByUserid(String userid);	
	
	//학생의 강좌 수강신청여부
	public Optional<RegisterEntity> findByUseridAndClassCode(String userid, String classCode);
	
	//완강 여부에 따른 강좌 리스트
	public Optional<List<RegisterEntity>> findAllByUseridAndClassFinishCheck(String userid, String clssFinishCheck);

	//강좌의 수강 학생 수
	@Query(value="SELECT COUNT (userid) FROM tbl_register WHERE class_code =:classCode",nativeQuery=true)
	public int regStudentCnt(@Param("classCode") String classCode);
	
	//강좌의 완강한 학생 수
	@Query(value="SELECT COUNT (userid) FROM tbl_register WHERE class_code =:classCode AND class_finish_check =:classFinishCheck",nativeQuery=true)
	public int finishedStudentCnt(@Param("classCode") String classCode, @Param("classFinishCheck") String classFinishCheck);
}
