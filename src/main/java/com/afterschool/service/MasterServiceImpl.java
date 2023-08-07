package com.afterschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.repository.ClassRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService{
	
	private final ClassRepository classRepository;
	
	//승인 요청 목록("W"만)
	@Override
	public List<ClassEntity> list(String approveStatus) {
		return classRepository.findAllByApproveStatus(approveStatus);
	}
	
	//classUploadCheckList
	@Override
	public ClassEntity view(String classCode) {
		return classRepository.findByClassCode(classCode);
	}
	
	//승인 수락
	@Override
	public ClassEntity approve(String classCode) {
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		classEntity.setApproveStatus("A");
		
		return classRepository.save(classEntity);
	}
	
	//승인 거절
	@Override
	public ClassEntity refuse(String classCode) {
		ClassEntity classEntity = classRepository.findByClassCode(classCode);
		classEntity.setApproveStatus("R");
		
		return classRepository.save(classEntity);
	}
}
