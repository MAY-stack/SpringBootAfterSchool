package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.NoticeBoardEntity;

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
public class NoticeBoardDTO {

	private Long noticeSeqno;
	private String userid;
//  FK
//  private MemberEntity userid;
	private String noticeWriter;
	private String noticeTitle;
	private LocalDateTime noticeRegdate;
    private String noticeContent;
    private String classCode;
    private String storedProfilename;
//  FK
//  private ClassEntity classCode;
    private int hitno;
		
	public NoticeBoardDTO(NoticeBoardEntity noticeBoardEntity) {
		
		this.noticeSeqno = noticeBoardEntity.getNoticeSeqno();
		this.userid = noticeBoardEntity.getUserid();
		this.noticeWriter = noticeBoardEntity.getNoticeWriter();
		this.noticeTitle = noticeBoardEntity.getNoticeTitle();
		this.noticeRegdate = noticeBoardEntity.getNoticeRegdate();
        this.noticeContent = noticeBoardEntity.getNoticeContent();
        this.classCode = noticeBoardEntity.getClassCode();
        this.hitno = noticeBoardEntity.getHitno();
        this.storedProfilename = noticeBoardEntity.getStoredProfilename();
		
	}
	
	public NoticeBoardEntity dtoToEntity(NoticeBoardDTO dto) {
		
		NoticeBoardEntity noticeboardEntity = NoticeBoardEntity.builder()
							.userid(dto.getUserid())
							.noticeWriter(dto.getNoticeWriter())
							.noticeSeqno(dto.getNoticeSeqno())
							.noticeTitle(dto.getNoticeTitle())
                            .noticeRegdate(dto.getNoticeRegdate())
                            .noticeContent(dto.getNoticeContent())
                            .classCode(dto.getClassCode())
                            .hitno(dto.getHitno())
                            .storedProfilename(dto.getStoredProfilename())
							.build();
		return noticeboardEntity;
	}
	
}