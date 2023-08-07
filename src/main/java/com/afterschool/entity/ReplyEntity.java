package com.afterschool.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="reply")
@Table(name="tbl_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
	    name = "reply_seq_generator",
	    sequenceName = "tbl_reply_seq",
	    initialValue=1,
	    allocationSize = 1)
public class ReplyEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reply_seq_generator")
    private Long replySeqno;

    @Column(name="seqno", nullable=false)
    private Long seqno;
//  FK
//	@ManyToOne(fetch = FetchType.LAZY)
//	@OnDelete(action = OnDeleteAction.CASCADE)  //사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="seqno", nullable=false)
//    private FreeBoardEntity seqno;
    
    @Column(name="userid", length=50, nullable=false)
    private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid")
//    private MemberEntity userid;

    @Column(name="reply_regdate", nullable=false)
    private LocalDateTime replyRegdate;

    @Column(name="reply_content", length=2000, nullable=false)
    private String replyContent;
    
    @Column(name="reply_writer", length=50, nullable=false)
    private String replyWriter;
    
    //FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="avatar", nullable = true)
//	private MemberEntity avatar;
    
    @Column(name="avatar", length=2, nullable=true)
    private String avatar;

}
