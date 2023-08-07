package com.afterschool.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.AddrEntity;
public interface AddrRepository extends JpaRepository<AddrEntity,Long>{
	Page<AddrEntity> findByRoadContainingOrBuildingContaining(String addrSearch1, String addrSearch2, Pageable pageable);
}
