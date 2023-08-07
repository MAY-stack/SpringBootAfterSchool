package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.ClassEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDTO {

	private String classCode;
	
//  FK
//  private MemberEntity userid;
    private String userid;

//  FK
//  private MemberEntity username;    
    private String username;
   
    private String category;
    private String className;
    private String classDescription;
    private String approveStatus;
    private String refuseReason;
    private String storedClassProfilename;
    private int lectureCount;
    private LocalDateTime classUploadDate;
   
    private int registeredStudentCnt;
    private int finishedStudentCnt;
    private double finishedRate;
    private double avgRate;

    //생성자를 이용해서 Entity를 DTO로 이동
	public ClassDTO(ClassEntity classEntity) {
		
		this.classCode  =  classEntity.getClassCode();
		this.userid  =  classEntity.getUserid();
		this.username = classEntity.getUsername();
		this.category = classEntity.getCategory();
		this.className = classEntity.getClassName();
		this.classDescription = classEntity.getClassDescription();
		this.approveStatus = classEntity.getApproveStatus();
		this.refuseReason = classEntity.getRefuseReason();
		this.storedClassProfilename = classEntity.getStoredClassProfilename();
		this.lectureCount = classEntity.getLectureCount();
		this.classUploadDate = classEntity.getClassUploadDate();
		this.registeredStudentCnt = classEntity.getRegisteredStudentCnt();
		this.finishedStudentCnt = classEntity.getFinishedStudentCnt();
		this.finishedRate = classEntity.getFinishedRate();
		this.avgRate = classEntity.getAvgRate();
		
	}

    //DTO --> Entity로 이동
	public ClassEntity dtoToEntity(ClassDTO dto) {
		
		ClassEntity classEntity = ClassEntity.builder()
				
								.classCode(dto.getClassCode())
								.userid(dto.getUserid())
								.username(dto.getUsername())
								.category(dto.getCategory())
								.className(dto.getClassName())
								.classDescription(dto.getClassDescription())
								.approveStatus(dto.getApproveStatus())
								.refuseReason(dto.getRefuseReason())
								.storedClassProfilename(dto.getStoredClassProfilename())
								.lectureCount(dto.getLectureCount())
								.classUploadDate(dto.getClassUploadDate())
								.registeredStudentCnt(dto.getRegisteredStudentCnt())
								.finishedStudentCnt(dto.getFinishedStudentCnt())
								.finishedRate(dto.getFinishedRate())
								.avgRate(dto.getAvgRate())
								.build();
		return classEntity;
	}

}
