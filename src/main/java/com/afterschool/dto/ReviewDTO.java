package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.ReviewEntity;

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
public class ReviewDTO {

	private Long reviewSeqno;
	private String classCode;
//  FK
//  private ClassEntity classCode;
    private String className; //0619 추가
	private String userid;
    private int rate;
    private LocalDateTime reviewDate;
    private String reviewContent;
    private String reviewWriter;    
		
	public ReviewDTO(ReviewEntity reviewEntity) {
		
		this.reviewSeqno = reviewEntity.getReviewSeqno();
		this.classCode = reviewEntity.getClassCode();
		this.className = reviewEntity.getClassName();
		this.userid = reviewEntity.getUserid();
		this.rate = reviewEntity.getRate();
		this.reviewDate = reviewEntity.getReviewDate();
        this.reviewContent = reviewEntity.getReviewContent();
        this.reviewWriter = reviewEntity.getReviewWriter();
		
	}
	
	public ReviewEntity dtoToEntity(ReviewDTO dto) {
		
		ReviewEntity reviewEntity = ReviewEntity.builder()
							.reviewSeqno(dto.getReviewSeqno())
							.classCode(dto.getClassCode())
							.className(dto.getClassName())
							.userid(dto.getUserid())
                            .rate(dto.getRate())
                            .reviewDate(dto.getReviewDate())
                            .reviewContent(dto.getReviewContent())
                            .reviewWriter(dto.getReviewWriter())
							.build();
		return reviewEntity;
	}
	
}