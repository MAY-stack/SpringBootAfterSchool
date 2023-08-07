package com.afterschool.category.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.category.entity.CategoryBEntity;

public interface CategoryBRepository extends JpaRepository<CategoryBEntity,String>{
	public CategoryBEntity findByClassName(String className);
}
