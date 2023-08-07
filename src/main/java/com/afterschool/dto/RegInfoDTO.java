package com.afterschool.dto;

import com.afterschool.entity.RegInfoEntity;

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
public class RegInfoDTO {

	private String userid;
	//FK
	//private MemeberEntity userid;
	private String lectureSeqno;
	//FK
	//private ClassEntity lectureSeqno;
    private String classCode;
    
	//FK
	//private ClassEntity classCode;
    private double playtime;
    private double playbar;
    private String finishCheck;
    
	public RegInfoDTO(RegInfoEntity regInfoEntity) {
		
		this.userid = regInfoEntity.getUserid();
		this.lectureSeqno = regInfoEntity.getLectureSeqno();
		this.classCode = regInfoEntity.getClassCode();
		this.playtime = regInfoEntity.getPlaytime();
		this.playbar = regInfoEntity.getPlaybar();
		this.finishCheck = regInfoEntity.getFinishCheck();
		
	}
	
	public RegInfoEntity dtoToEntity(RegInfoDTO dto) {
		
		RegInfoEntity regInfoEntity = RegInfoEntity.builder()
								.userid(dto.getUserid())
								.lectureSeqno(dto.getLectureSeqno())
								.classCode(dto.getClassCode())
								.playtime(dto.getPlaytime())
								.playbar(dto.getPlaybar())
								.finishCheck(dto.getFinishCheck())
								.build();
		return regInfoEntity;
		
	}
	
	
}
