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

@Entity(name="wish")
@Table(name="tbl_wish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
	    name = "wish_seq_generator",
	    sequenceName = "tbl_wish_seq",
	    initialValue=1, 
	    allocationSize = 1)
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="wish_seq_generator")
    private Long wishSeqno;

    @Column(name="userid", length=50, nullable=false)
    private String userid;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="userid")
//    private MemberEntity userid;
    
    @Column(name="class_code", length=30, nullable=false)
    private String classCode;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="class_code")
//    private ClassEntity classCode;

    @Column(name="wishdate", nullable=false)
    private LocalDateTime wishdate;

    @Column(name="class_name", length=30, nullable=false)
    private String className;
}
