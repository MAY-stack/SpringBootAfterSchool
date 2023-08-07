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

@Entity(name="review")
@Table(name="tbl_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
	    name = "review_seq_generator",
	    sequenceName = "tbl_review_seq",
		initialValue=1,
	    allocationSize = 1)

public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="review_seq_generator")
    private Long reviewSeqno;

    @Column(name="class_code", length=30, nullable=true)
    private String classCode;
    
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="class_code")
//    private ClassEntity classCode;

    //0619 추가
    @Column(name="class_name", length=50, nullable=true)
    private String className;

    //0619 추가
    @Column(name="review_writer", length=50, nullable=true, unique=true)
    private String reviewWriter;
    
    @Column(name="userid", length=50, nullable=false)
    private String userid;

    @Column(name="rate", nullable=false, columnDefinition = "number(5) default 0")
    private int rate;
    
    @Column(name="review_date", length=20, nullable = true)
    private LocalDateTime reviewDate;
    
    @Column(name="review_content", length=50, nullable=false)
    private String reviewContent;

}
