package com.afterschool.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

//insert, update 시 null값은 제외
@DynamicInsert
@DynamicUpdate

@Entity(name="freeboard")
@Table(name="tbl_freeboard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
	    name = "freeboard_seq_generator",
	    sequenceName = "tbl_freeboard_seq",
		initialValue=1, 
	    allocationSize = 1)

public class FreeBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="freeboard_seq_generator")
    private Long seqno;

    @Column(name="userid", length=50, nullable = false)
    private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid", nullable = false)
//    private MemberEntity userid;

    @Column(name="writer", length=20, nullable=false)
    private String writer;
    
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="writer")
//    private MemberEntity username; 
   
    
    @Column(name="title", length=50, nullable=false)
    private String title;

    @Column(name="regdate", nullable=false)
    private LocalDateTime regdate;

    @Column(name="content", length=2000, nullable=false)
    private String content;

    @Column(name="hitno", nullable=true)
    private int hitno;

    @Column(name="like_cnt", nullable=false)
    private int likeCnt;
    
    @Column(name="avatar", length=2, nullable=true)
    private String avatar;
    
    //FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="avater", nullable = true)
//	private MemberEntity avater;

}
