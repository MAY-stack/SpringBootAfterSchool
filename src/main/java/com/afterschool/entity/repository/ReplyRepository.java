package com.afterschool.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afterschool.dto.ReplyInterface;
import com.afterschool.entity.ReplyEntity;
public interface ReplyRepository extends JpaRepository<ReplyEntity,Long>{
	
	@Query(value="select reply_seqno as replySeqno, reply_writer as replyWriter, reply_content as replyContent, reply_regdate as replyRegdate, seqno, userid, avatar from tbl_reply where seqno=:seqno order by reply_seqno desc",nativeQuery=true)
	List<ReplyInterface> replyView(@Param("seqno") Long seqno);
	
}
