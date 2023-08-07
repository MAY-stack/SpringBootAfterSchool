package com.afterschool.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afterschool.entity.ReviewEntity;
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long>{
		
	//강좌의 리뷰 목록
	public List<ReviewEntity> findAllByClassCode(String classCode);
	
	//해당 강좌의 평점
	@Query(value="SELECT AVG(rate) FROM tbl_review WHERE class_code =:classCode GROUP BY class_code",nativeQuery=true)
	public double getClassAvg(@Param("classCode") String classCode);
	
	//나가 쓴 리뷰 목록
	public List<ReviewEntity> findAllByUserid(String userid);
	
}
