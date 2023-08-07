package com.afterschool.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//insert, update 시 null값은 제외
@DynamicInsert
@DynamicUpdate

@Entity(name="lecture")
@Table(name="tbl_lecture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureEntity {

	@Id
	@Column(name="lecture_seqno", length=30)
	private String lectureSeqno;	
	
    @Column(name="class_code", length=30, nullable=false)
    private String classCode;
//  FK
//	@ManyToOne(fetch=FetchType.LAZY)
//	@OnDelete(action=OnDeleteAction.CASCADE)	//사용자가 삭제되어도 작성한 글 삭제되지 않도록함
//	@JoinColumn(name="class_code")
//    private ClassEntity classCode;
    
    @Column(name="class_name", length=50, nullable=true)
    private String className;

    @Column(name="stored_videoname", length=200, nullable=false)
    private String storedVideoname;

    @Column(name="lecture_title", length=50, nullable=false)
    private String lectureTitle;
    
    @Column(name="video_use_check", length=2, nullable=true)
    @ColumnDefault("'Y'")
    private String videoUseCheck;    

}
