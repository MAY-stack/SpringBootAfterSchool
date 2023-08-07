package com.afterschool.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.LectureEntity;
public interface LectureRepository extends JpaRepository<LectureEntity,Long>{
	
	//강좌의 강의 리스트
	public List<LectureEntity> findAllByClassCode(String classCode); 
	
}
