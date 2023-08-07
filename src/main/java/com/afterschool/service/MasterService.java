package com.afterschool.service;

import java.util.List;

import com.afterschool.entity.ClassEntity;

public interface MasterService {
	
	//승인 요청 목록("W"만)
	public List<ClassEntity> list(String approveStatus);
	
	//classUploadCheckList
	public ClassEntity view(String classCode);
	
	//승인 수락
	public ClassEntity approve(String classCode);
	
	//승인 거절
	public ClassEntity refuse(String classCode);
}
