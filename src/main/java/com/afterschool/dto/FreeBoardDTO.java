package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.FreeBoardEntity;

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
public class FreeBoardDTO {
	
    private Long seqno;
//    FK
    private String userid;
//    private MemberEntity userid;
    private String writer;
//  FK
//  private MemberEntity username;
    private String title;
    private LocalDateTime regdate;
    private String content;
    private int hitno;
    private int likeCnt;
    private String avatar;
    //private MemberEntity avatar;
	
	//생성자를 이용해서 Entity를 DTO로 이동
	public FreeBoardDTO(FreeBoardEntity freeBoardEntity) {
		
		this.seqno = freeBoardEntity.getSeqno();
		this.userid = freeBoardEntity.getUserid();
		this.writer = freeBoardEntity.getWriter();
		this.title = freeBoardEntity.getTitle();
		this.regdate = freeBoardEntity.getRegdate();
		this.content = freeBoardEntity.getContent();
		this.hitno = freeBoardEntity.getHitno();
		this.likeCnt = freeBoardEntity.getLikeCnt();
		this.avatar = freeBoardEntity.getAvatar();
		
	}
	
	//DTO --> Entity로 이동
	public FreeBoardEntity dtoToEntity(FreeBoardDTO dto) {
		
		FreeBoardEntity freeboardEntity = FreeBoardEntity.builder()
								.userid(dto.getUserid())
								.writer(dto.getWriter())		
								.seqno(dto.getSeqno())
								.title(dto.getTitle())
								.regdate(dto.getRegdate())
								.content(dto.getContent())
								.hitno(dto.getHitno())
								.likeCnt(dto.getLikeCnt())
								.avatar(dto.getAvatar())
								.build();
		return freeboardEntity;
		
	}
	
}