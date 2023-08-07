package com.afterschool.entity;

import java.time.LocalDateTime;

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

@Entity(name="classes")
@Table(name="tbl_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassEntity {
    
	@Id
	@Column(name="class_code", length=50)
	private String classCode;

    @Column(name="userid", length=50, nullable=true)
    private String userid;
    
    @Column(name="username", length=20, nullable=true)
    private String username;
    
    @Column(name="category", length=20, nullable=true)
    private String category;

    @Column(name="class_name", length=50, nullable=true)
    private String className;
    
    @Column(name="class_description", length=200, nullable=true)
    private String classDescription;
    
    @Column(name="approve_status", length=20, nullable=true)
    @ColumnDefault("'W'")
    private String approveStatus;
    
    @Column(name="refuse_reason", length=50, nullable=true)
    private String refuseReason;
    
    @Column(name="stored_class_profilename", length=50, nullable=true)
    private String storedClassProfilename;

    @Column(name="lecture_count", nullable=true)
    private int lectureCount;
    
    @Column(name="class_upload_date", nullable=true)
    private LocalDateTime classUploadDate;
        
    @Column(name="registered_student_cnt", nullable=false)
    @ColumnDefault("0")
    private int registeredStudentCnt;

    @Column(name="finished_student_cnt", nullable=false)
    @ColumnDefault("0")
    private int finishedStudentCnt;
    
    @Column(name="finished_rate", columnDefinition = "Number(7,2) default 0")
    private double finishedRate;
    
    @Column(name="avg_rate", nullable=true, columnDefinition = "Number(3,2) default 0")
    private double avgRate;
    
}