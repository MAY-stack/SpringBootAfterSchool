package com.afterschool.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.afterschool.entity.NoticeBoardEntity;
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity,Long>{
	
	//게시물 목록 보기
	public Page<NoticeBoardEntity> findByClassCode(String classCode, Pageable pageable);
	
	//게시물 시퀀스 번호 가져오기
	@Query(value="select tbl_noticeboard_seq.currval  from dual", nativeQuery=true)
	public Long getSeqnoWithNextval();
	
	//게시물 조회수 증가
	@Transactional
	@Modifying //테이블에 반영된 내용이 엔티티 클래스에 적용될수 있도록 함.
	@Query(value="update tbl_noticeboard set hitno = (select nvl(hitno,0) from tbl_noticeboard where notice_seqno=:noticeSeqno) + 1 where notice_seqno=:noticeSeqno", nativeQuery=true)
	public void updateHitno(@Param("noticeSeqno") Long noticeSeqno);
	
	//게시물 이전 보기 - JPQL(Java Persistent Query Language)
	//@Query("select max(b.noticeSeqno) from noticeboard b where b.noticeSeqno < :noticeSeqno and b.classCode=:classCode")
	//public Long findPreSeqno(@Param("noticeSeqno") Long noticeSeqno, @Param("classCode") String classCode);
	
	@Query(value="select nvl(max(notice_seqno),0) from tbl_noticeboard where notice_seqno < :noticeSeqno and class_code=:classCode", nativeQuery=true)
	public Long findPreSeqno(@Param("noticeSeqno") Long noticeSeqno, @Param("classCode") String classCode);
	
	//게시물 다음 보기
	@Query(value="select nvl(min(notice_seqno),0) from tbl_noticeboard where notice_seqno > :noticeSeqno and class_code=:classCode", nativeQuery=true)
	public Long findNextSeqno(@Param("noticeSeqno") Long noticeSeqno, @Param("classCode") String classCode); 

	//게시물 삭제
	public NoticeBoardEntity findByNoticeSeqnoAndClassCode(Long noticeSeqno, String classCode);

}
