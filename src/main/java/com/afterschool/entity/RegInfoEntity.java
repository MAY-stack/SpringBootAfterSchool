package com.afterschool.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//insert, update 시 null값은 제외
@DynamicInsert
@DynamicUpdate

@Entity(name="reginfo")
@Table(name="tbl_reginfo")
@IdClass(RegInfoEntityID.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegInfoEntity {
	
    @Id
	@Column(name="userid", length=50, nullable=false)
	private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid")
//    private MemberEntity userid;

	@Id
    @Column(name="lecture_seqno", length=30, nullable=false)
    private String lectureSeqno;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="lecture_seqno")
//    private ClassEntity lectureSeqno;

    @Column(name="class_code", length=30, nullable=false)
    private String classCode;

    @Column(name="playtime", columnDefinition = "Number(7,2) default 0")
    private double playtime;
    
    @Column(name="playbar", columnDefinition = "Number(7,2) default 0")
    private double playbar;

    @Column(name="finish_check", length=5, nullable=true)
    @ColumnDefault("'N'")
    private String finishCheck;
    
}
