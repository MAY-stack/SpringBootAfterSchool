package com.afterschool.category.service;

import org.springframework.stereotype.Service;

import com.afterschool.category.entity.CategoryAEntity;
import com.afterschool.category.entity.CategoryBEntity;
import com.afterschool.category.entity.CategoryCEntity;
import com.afterschool.category.entity.CategoryDEntity;
import com.afterschool.category.entity.repository.CategoryARepository;
import com.afterschool.category.entity.repository.CategoryBRepository;
import com.afterschool.category.entity.repository.CategoryCRepository;
import com.afterschool.category.entity.repository.CategoryDRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private final CategoryARepository categoryARepository;
	private final CategoryBRepository categoryBRepository;
	private final CategoryCRepository categoryCRepository;
	private final CategoryDRepository categoryDRepository;

	//사용자강좌 등록
	@Override
	public void applyA(CategoryAEntity categoryAEntity) {		
		categoryARepository.save(categoryAEntity);
	}
	@Override
	public void applyB(CategoryBEntity categoryBEntity) {		
		categoryBRepository.save(categoryBEntity);
	}
	@Override
	public void applyC(CategoryCEntity categoryCEntity) {		
		categoryCRepository.save(categoryCEntity);
	}
	@Override
	public void applyD(CategoryDEntity categoryDEntity) {		
		categoryDRepository.save(categoryDEntity);
	}
	
	//강좌 정보 등록 및 Class code 생성
	public String genClassCode(String category, String className) {
		
		String classCode = "";
		Long classSeq = 0L;
		if(category.equals("A")) {			
			classSeq = categoryARepository.findByClassName(className).getASeq();
			classCode = "A";
		}
		
		if(category.equals("B")) {
			classSeq = categoryBRepository.findByClassName(className).getBSeq();
			classCode = "B";
		}
		
		if(category.equals("C")) {
			classSeq = categoryCRepository.findByClassName(className).getCSeq();
			classCode = "C";
		}
		
		if(category.equals("D")) {
			classSeq = categoryDRepository.findByClassName(className).getDSeq();
			classCode = "D";
		}
		System.out.println("변환 전");
		String seqStr = String.format("%04d", classSeq.intValue());
		System.out.println(seqStr);
		classCode += seqStr; 
		return classCode;
	}

}
