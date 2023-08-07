package com.afterschool.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.WishEntity;

public interface WishRepository extends JpaRepository<WishEntity,Long>{
	
	//학생의 찜하기 정보 가져오기
	public Optional<WishEntity> findByClassCodeAndUserid(String classCode, String userid);
	
	//학생의 찜하기 강좌 리스트
	public List<WishEntity> findAllByUserid(String userid);
	
	//추가
	//학생의 수강 신청 강좌
	public Optional<WishEntity> findByUseridAndClassCode(String userid, String classCode);

}
