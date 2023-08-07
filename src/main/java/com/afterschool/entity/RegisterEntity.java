package com.afterschool.entity;

import java.time.LocalDateTime;

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

@Entity(name="register")
@Table(name="tbl_register")
@IdClass(RegisterEntityID.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 

public class RegisterEntity {
 
	@Id
    @Column(name="class_code", length=50)
    private String classCode;

    @Column(name="userid", length=50, nullable=false)
    private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid")
//    private MemberEntity userid;

    @Column(name="class_name", length=50, nullable=true)
    private String className;

    @Column(name="lecture_seqno", length=50, nullable=false)
    private String lectureSeqno;
    
    @Column(name="playbar", columnDefinition = "Number(7,2) default 0")
    private double playbar;

    @Column(name="regdate", length=20, nullable=true)
    private LocalDateTime regdate;

    @Column(name="finished_lecture_cnt", nullable=false)
    @ColumnDefault("0")
    private int finishedLectureCnt;
    
    @Column(name="lecture_count", nullable=false)
    private int lectureCount;    
    
    @Column(name="class_finish_check", length=20, nullable=true)
    @ColumnDefault("'N'")
    private String classFinishCheck;
    
    @Column(name="finish_date", length=20, nullable=true)
    private LocalDateTime finishDate;
    
    @Column(name="review_check", length=2, nullable=true)
    @ColumnDefault("'N'")
    private String reviewCheck;
    
    @Column(name="percent", columnDefinition = "Number(7,2) default 0")
    private float percent;
}


