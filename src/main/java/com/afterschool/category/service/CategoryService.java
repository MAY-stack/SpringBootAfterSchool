package com.afterschool.category.service;

import com.afterschool.category.entity.CategoryAEntity;
import com.afterschool.category.entity.CategoryBEntity;
import com.afterschool.category.entity.CategoryCEntity;
import com.afterschool.category.entity.CategoryDEntity;

public interface CategoryService {
	
	//사용자강좌 등록
	public void applyA(CategoryAEntity categoryAEntity);
	public void applyB(CategoryBEntity categoryBEntity);
	public void applyC(CategoryCEntity categoryCEntity);
	public void applyD(CategoryDEntity categoryDEntity);
	
	//강좌코드 생성
	public String genClassCode(String Category, String className);
	
}
