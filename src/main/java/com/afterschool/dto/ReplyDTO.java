package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.ReplyEntity;

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
public class ReplyDTO {

	private Long replySeqno;
	private Long seqno;
//  FK
//	private FreeBoardEntity seqno;
	private String userid;
//  FK
//  private MemberEntity userid;
    private LocalDateTime replyRegdate;
    private String replyContent;
    private String replyWriter;
    private String avatar;
//    private MemberEntity avatar;
		
	public ReplyDTO(ReplyEntity replyEntity) {
		
		this.replySeqno = replyEntity.getReplySeqno();
		this.seqno = replyEntity.getSeqno();
		this.userid = replyEntity.getUserid();
		this.replyRegdate = replyEntity.getReplyRegdate();
        this.replyContent = replyEntity.getReplyContent();
        this.replyWriter = replyEntity.getReplyWriter();
        this.avatar = replyEntity.getAvatar();	
	}
	
	public ReplyEntity dtoToEntity(ReplyDTO dto) {
		
		ReplyEntity replyEntity = ReplyEntity.builder()
							.replySeqno(dto.getReplySeqno())
							.seqno(dto.getSeqno())
							.userid(dto.getUserid())
                            .replyRegdate(dto.getReplyRegdate())
                            .replyContent(dto.getReplyContent())
                            .replyWriter(dto.getReplyWriter())
                            .avatar(dto.getAvatar())
							.build();
		return replyEntity;
	}
	
}