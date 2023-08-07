package com.afterschool.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.afterschool.entity.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, Long>{
	
	//강사의 전체 강좌 목록
	public List<ClassEntity> findAllByUserid(String userid);
	
	//강사의 승인상태 별 강좌 목록
	public List<ClassEntity> findAllByUseridAndApproveStatus(String userid, String approveStatus);
	
	//카테고리별 강좌 목록
	public List<ClassEntity> findAllByCategory(String category);
	
	//강의 검색 결과
	public List<ClassEntity> findAllByClassNameContaining(String keyword);
	
	//카테고리별 강좌 검색 결과
	public List<ClassEntity> findAllByCategoryContainingOrClassNameContaining(String category, String keyword);
	
	/* 승인상태 별  강좌 목록 */
	//카테고리별 강좌 목록
	public List<ClassEntity> findAllByCategoryAndApproveStatus(String category, String approveStatus);
	
	//강의 검색 결과
	public List<ClassEntity> findAllByClassNameContainingAndApproveStatus(String keyword, String approveStatus);
	
	//카테고리별 강좌 검색 결과
	public List<ClassEntity> findAllByCategoryContainingOrClassNameContainingAndApproveStatus(String category, String keyword, String approveStatus);
	
	//특정강좌 정보
	public ClassEntity findByClassCode(String classCode);
	
	//강좌명 중복 체크
	public Optional<ClassEntity> findByClassName(String className);
	
	//승인 요청 목록("W")
	public List<ClassEntity> findAllByApproveStatus(String approveStatus);
	
	//완강률 가장 높은 강좌 뽑기
	@Query(value ="select * from tbl_class where rownum < 2 order by finished_rate desc", nativeQuery=true)
	public ClassEntity getOneTopClass();
	
}
