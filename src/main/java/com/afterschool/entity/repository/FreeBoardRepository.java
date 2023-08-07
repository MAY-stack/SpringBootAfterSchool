package com.afterschool.entity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.afterschool.entity.FreeBoardEntity;
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity,Long>{
	
	//게시물 목록 보기
	public Page<FreeBoardEntity> findByWriterContainingOrTitleContainingOrContentContaining(String keyword,
	String keyword2, String keyword3, PageRequest pageRequest);
	
	//게시물 시퀀스 번호 가져오기
	@Query(value="select tbl_freeboard_seq.nextval from dual", nativeQuery=true)
	public Long getSeqnoWithNextval();
	
	//게시물 조회수 증가
	@Transactional
	@Modifying //테이블에 반영된 내용이 엔티티 클래스에 적용될수 있도록 함.
	@Query(value="update tbl_freeboard set hitno = (select nvl(hitno,0) from tbl_freeboard where seqno=:seqno) + 1 where seqno=:seqno", nativeQuery=true)
	public void updateHitno(@Param("seqno") Long seqno);
	
	//게시물 이전 보기 - JPQL(Java Persistent Query Language)
	@Query("select max(b.seqno) from freeboard b where b.seqno < :seqno and (b.writer like %:keyword1% or b.title like %:keyword2% or b.content like %:keyword3%)")
	public Long findPreSeqno(@Param("seqno") Long seqno, @Param("keyword1") String keyword1,@Param("keyword2") String keyword2,@Param("keyword3") String keyword3); 
	
	//게시물 다음 보기 - JPQL(Java Persistent Query Language)
	@Query("select min(b.seqno) from freeboard b where b.seqno > :seqno and (b.writer like %:keyword1% or b.title like %:keyword2% or b.content like %:keyword3%)")
	public Long findNextSeqno(@Param("seqno") Long seqno, @Param("keyword1") String keyword1,@Param("keyword2") String keyword2,@Param("keyword3") String keyword3);  
	
	//자유게시판 최근글 정보 5개(6/19 민경 추가)
	@Query(value="SELECT * from tbl_freeboard WHERE rownum < 6 order by regdate desc",nativeQuery=true)
	public List<FreeBoardEntity> getRecent5FreeBoard();	
	
}
