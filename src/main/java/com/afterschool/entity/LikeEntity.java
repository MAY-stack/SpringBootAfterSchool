package com.afterschool.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//insert, update 시 null값은 제외
@DynamicInsert
@DynamicUpdate

@Entity(name="like")
@Table(name="tbl_like")
@Getter
@Setter
@IdClass(LikeEntityID.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "seqno")
	private FreeBoardEntity seqno;
//	@Column(name="seqno")
//    private Long seqno;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userid")
	private MemberEntity userid;
//	@Column(name="userid", length=50, nullable=false)
//	private String userid;

    @Column(name="like_check", length=2, nullable=false)
    @ColumnDefault("'N'")
    private String likeCheck;

}