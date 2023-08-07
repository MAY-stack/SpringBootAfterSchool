package com.afterschool.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.LikeEntity;
import com.afterschool.entity.LikeEntityID;
import com.afterschool.entity.MemberEntity;
public interface LikeRepository extends JpaRepository<LikeEntity,LikeEntityID>{
	
	public LikeEntity findBySeqnoAndUserid(FreeBoardEntity freeBoard, MemberEntity member);
//	public LikeEntity findBySeqnoAndUserid(Long seqno, String userid);
	
}
