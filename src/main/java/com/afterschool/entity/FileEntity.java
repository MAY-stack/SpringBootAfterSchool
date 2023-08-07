package com.afterschool.entity;

import org.hibernate.annotations.ColumnDefault;
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

@Entity(name="file")
@Table(name="tbl_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
	    name = "file_seq_generator",
	    sequenceName = "tbl_file_seq",
		initialValue=1, 
	    allocationSize = 1)

public class FileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="file_seq_generator")
    private Long fileSeqno;

    @Column(name="notice_seqno", nullable=false)
    private Long noticeSeqno;
    
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="notice_seqno")
//    private FreeBoardEntity noticeSeqno;

    @Column(name="org_filename", length=200, nullable=false)
    private String orgFilename;

    @Column(name="stored_filename", length=200, nullable=false)
    private String storedFilename;

    @Column(name="filesize", nullable=false)
    private Long filesize;

    @Column(name="userid", length=50, nullable=false)
    private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid")
//    private MemberEntity userid;

    @Column(name="checkfile", length=2, nullable=false)
    @ColumnDefault("'Y'")
    private String checkfile;

}