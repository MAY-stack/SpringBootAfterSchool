package com.afterschool.dto;

import java.time.LocalDateTime;

import com.afterschool.entity.WishEntity;

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
public class WishDTO {

	private Long wishSeqno;
	private String userid;
//  FK
//  private MemberEntity userid;
    private String classCode;
//  FK
//  private ClassEntity classCode;
    private LocalDateTime wishdate;
    private String className;
		
	public WishDTO(WishEntity wishEntity) {
		
		this.wishSeqno = wishEntity.getWishSeqno();
        this.userid = wishEntity.getUserid();
		this.classCode = wishEntity.getClassCode();
		this.wishdate = wishEntity.getWishdate();
		this.className = wishEntity.getClassName();
		
	}
	
	public WishEntity dtoToEntity(WishDTO dto) {
		
		WishEntity wishEntity = WishEntity.builder()
							.wishSeqno(dto.getWishSeqno())
                            .userid(dto.getUserid())
							.classCode(dto.getClassCode())
                            .wishdate(dto.getWishdate())
                            .className(dto.getClassName())
							.build();
		return wishEntity;
	}
	
}