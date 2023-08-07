package com.afterschool.dto;

import com.afterschool.entity.LectureEntity;

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
public class LectureDTO {
	
    private String lectureSeqno;
    private String classCode;
    private String className;
//  FK
//  private ClassEntity classCode;
    private String storedVideoname;
    private String lectureTitle;
	
	//생성자를 이용해서 Entity를 DTO로 이동
	public LectureDTO(LectureEntity lectureEntity) {
		
		this.lectureSeqno = lectureEntity.getLectureSeqno();
		this.classCode = lectureEntity.getClassCode();
		this.className = lectureEntity.getClassName();
		this.storedVideoname = lectureEntity.getStoredVideoname();
		this.lectureTitle = lectureEntity.getLectureTitle();
		
	}
	
	//DTO --> Entity로 이동
	public LectureEntity dtoToEntity(LectureDTO dto) {
		
		LectureEntity lectureEntity = LectureEntity.builder()
								.lectureSeqno(dto.getLectureSeqno())
								.classCode(dto.getClassCode())
								.className(dto.getClassName())
								.storedVideoname(dto.getStoredVideoname())
								.lectureTitle(dto.getLectureTitle())
								.build();
		return lectureEntity;
		
	}
	
}