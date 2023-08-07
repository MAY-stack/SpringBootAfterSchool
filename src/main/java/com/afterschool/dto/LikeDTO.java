package com.afterschool.dto;

import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.LikeEntity;
import com.afterschool.entity.MemberEntity;

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
public class LikeDTO {

	//private Long seqno;
	private FreeBoardEntity seqno;
	//private String userid;
	private MemberEntity userid;
	private String likeCheck;
		
	public LikeDTO(LikeEntity likeEntity) {
		
		this.seqno = likeEntity.getSeqno();
		this.userid = likeEntity.getUserid();
		this.likeCheck = likeEntity.getLikeCheck();		
	}
	
	public LikeEntity dtoToEntity(LikeDTO dto) {
		
		LikeEntity likeEntity = LikeEntity.builder()
							.seqno(dto.getSeqno())
							.userid(dto.getUserid())
							.likeCheck(dto.getLikeCheck())
							.build();
		return likeEntity;
	}
	
}
