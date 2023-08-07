package com.afterschool.category.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.category.entity.CategoryCEntity;

public interface CategoryCRepository extends JpaRepository<CategoryCEntity,String>{
	public CategoryCEntity findByClassName(String className);	
}
