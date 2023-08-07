package com.afterschool.category.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.category.entity.CategoryAEntity;

public interface CategoryARepository extends JpaRepository<CategoryAEntity,String>{
	public CategoryAEntity findByClassName(String className);	
}
