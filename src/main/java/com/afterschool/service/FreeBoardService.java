package com.afterschool.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.afterschool.dto.FreeBoardDTO;
import com.afterschool.dto.ReplyInterface;
import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.LikeEntity;

public interface FreeBoardService {
	
	//게시물 목록 보기
	public Page<FreeBoardEntity> list(int startPoint,int postNum, String keyword);
	
	//게시물 번호 구하기
	public Long getSeqnoWithNextval();
	
	//게시물 등록
	public void write(FreeBoardDTO freeboard);
	
	//게시물 상세 보기
	public FreeBoardDTO view(Long seqno);
	
	//게시물 수정 
	public void modify(FreeBoardDTO freeboard);
	
	//게시물 삭제
	public void delete(Long seqno);
	
	//이전 보기 
	public Long pre_seqno(Long seqno, String keyword);
	
	//다음 보기
	public Long next_seqno(Long seqno, String keyword);
	
	//조회수 업데이트
	public void hitno(FreeBoardDTO freeboard);
	
	//내가 한 좋아요 확인 가져 오기
	public LikeEntity likeCheckView(Long seqno,String userid) throws Exception;
	
	//좋아요 갯수 수정하기
	public void boardLikeUpdate(Long seqno, int likeCnt) throws Exception;
	
	//좋아요 확인 등록하기
	public void likeCheckRegistry(Long seqno,String userid,String likeCheck) throws Exception;
	
	//좋아요 확인 수정하기
	public void likeCheckUpdate(Long seqno,String userid,String likeCheck) throws Exception;
	
	//댓글 보기
	public List<ReplyInterface> replyView(ReplyInterface reply) throws Exception;
		
	//댓글 수정
	public void replyUpdate(ReplyInterface reply) throws Exception;
		
	//댓글 등록 
	public void replyRegistry(ReplyInterface reply) throws Exception;
		
	//댓글 삭제
	public void replyDelete(ReplyInterface reply) throws Exception;
}
