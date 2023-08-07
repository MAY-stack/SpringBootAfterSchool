package com.afterschool.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode //변수값들을 serialize화 하기 위해 필요한 작업을 해 줌  
public class RegInfoEntityID implements Serializable{

	private String userid;
	private String lectureSeqno;
	
}
