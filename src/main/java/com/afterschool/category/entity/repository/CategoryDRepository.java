package com.afterschool.category.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.category.entity.CategoryDEntity;

public interface CategoryDRepository extends JpaRepository<CategoryDEntity,String>{

	public CategoryDEntity findByClassName(String className);
}
