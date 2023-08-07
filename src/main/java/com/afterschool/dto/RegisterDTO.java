package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.RegisterEntity;

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
public class RegisterDTO {

	private String classCode;
	private String className;
	private String userid;
	private String lectureSeqno;
	private double playbar;
    private LocalDateTime regdate;
    private LocalDateTime recentLogindate;
    private String classFinishCheck;
    private LocalDateTime finishDate;
    private float percent;
		
	public RegisterDTO(RegisterEntity registerEntity) {
		
		this.classCode = registerEntity.getClassCode();
		this.className = registerEntity.getClassName();
		this.userid = registerEntity.getUserid();
		this.lectureSeqno = registerEntity.getLectureSeqno();
		this.playbar = registerEntity.getPlaybar();
		this.regdate = registerEntity.getRegdate();
		this.classFinishCheck = registerEntity.getClassFinishCheck();
		this.finishDate = registerEntity.getFinishDate();
		this.percent = registerEntity.getPercent();
		
	}
	
	public RegisterEntity dtoToEntity(RegisterDTO dto) {
		
		RegisterEntity registerEntity = RegisterEntity.builder()
										.classCode(dto.getClassCode())
										.className(dto.getClassName())
										.userid(dto.getUserid())
										.lectureSeqno(dto.getLectureSeqno())
										.playbar(dto.getPlaybar())
										.regdate(dto.getRegdate())
										.classFinishCheck(dto.getClassFinishCheck())
										.finishDate(dto.getFinishDate())
										.percent(dto.getPercent())
										.build();
		return registerEntity;
	}
	
}