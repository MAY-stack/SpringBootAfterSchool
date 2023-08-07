package com.afterschool.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.SchoolEntity;
public interface SchoolRepository extends JpaRepository<SchoolEntity,Long>{
	
	Page<SchoolEntity> findBySchoolNameContaining(String schoolSearch, Pageable pageable);
	
}
