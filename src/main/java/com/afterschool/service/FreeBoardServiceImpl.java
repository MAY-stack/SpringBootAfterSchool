package com.afterschool.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.afterschool.dto.FreeBoardDTO;
import com.afterschool.dto.ReplyInterface;
import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.LikeEntity;
import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.ReplyEntity;
import com.afterschool.entity.repository.FreeBoardRepository;
import com.afterschool.entity.repository.LikeRepository;
import com.afterschool.entity.repository.MemberRepository;
import com.afterschool.entity.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService{
	
	private final FreeBoardRepository freeBoardRepository;
	private final MemberRepository memberRepository;
	private final LikeRepository likeRepository;
	private final ReplyRepository replyRepository;
	
	//게시물 목록 보기
	@Override
	public Page<FreeBoardEntity> list(int pageNum,int postNum, String keyword){
		
		PageRequest pageRequest = PageRequest.of(pageNum-1, postNum, Sort.by(Direction.DESC,"seqno"));
		return freeBoardRepository.findByWriterContainingOrTitleContainingOrContentContaining(keyword, keyword, keyword, pageRequest);
	}
	
	//게시물 번호 구하기
	@Override
	public Long getSeqnoWithNextval() {
		return freeBoardRepository.getSeqnoWithNextval();
	}
	
	//게시물 등록
	@Override
	public void write(FreeBoardDTO freeboard) {
		freeboard.setRegdate(LocalDateTime.now());
		freeBoardRepository.save(freeboard.dtoToEntity(freeboard));
	}
	
	//게시물 상세 보기
	@Override
	public FreeBoardDTO view(Long seqno) {
		return freeBoardRepository.findById(seqno).map(view-> new FreeBoardDTO(view)).get();
	}
	
	//게시물 수정 
	@Override
	public void modify(FreeBoardDTO freeboard) {		
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(freeboard.getSeqno()).get();
		freeBoardEntity.setTitle(freeboard.getTitle());
		freeBoardEntity.setContent(freeboard.getContent());		
		freeBoardRepository.save(freeBoardEntity);
	}
	
	//게시물 삭제
	@Override
	public void delete(Long seqno) {
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(seqno).get();
		freeBoardRepository.delete(freeBoardEntity);
	}
	
	//이전 보기 
	@Override
	public Long pre_seqno(Long seqno, String keyword) {
		return freeBoardRepository.findPreSeqno(seqno, keyword, keyword, keyword)==null?0:freeBoardRepository.findPreSeqno(seqno, keyword, keyword, keyword);
	}
	
	//다음 보기
	@Override
	public Long next_seqno(Long seqno, String keyword) {
		return freeBoardRepository.findNextSeqno(seqno, keyword, keyword, keyword)==null?0:freeBoardRepository.findNextSeqno(seqno, keyword, keyword, keyword);
	}
	
	//조회수 업데이트
	@Override
	public void hitno(FreeBoardDTO freeboard) {
		freeBoardRepository.updateHitno(freeboard.getSeqno());
	}

	//좋아요 테이블에서 mylikeckeck 값(Y,N) 가져 오기
	@Override
	public LikeEntity likeCheckView(Long seqno,String userid) throws Exception{
		System.out.println("seqno is " + seqno);
		System.out.println("userid is " + userid);
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(seqno).get();
		System.out.println("here1");
		MemberEntity memberEntity = memberRepository.findById(userid).get();
		System.out.println("here2");
		return likeRepository.findBySeqnoAndUserid(freeBoardEntity, memberEntity);
		//return likeRepository.findBySeqnoAndUserid(seqno, userid);
	}
	
	//좋아요/싫어요 테이블에 등록
	@Override
	public void likeCheckRegistry(Long seqno,String userid,String likeCheck) throws Exception {
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(seqno).get();
		MemberEntity memberEntity = memberRepository.findById(userid).get();	
		LikeEntity likeEntity = LikeEntity.builder()
		.seqno(freeBoardEntity)
		.userid(memberEntity)
		.likeCheck(likeCheck)
		.build();
		likeRepository.save(likeEntity);
	}
	
	//좋아요테이블에서 likeCkeck 값(Y,N)을 수정
	@Override
	public void likeCheckUpdate(Long seqno,String userid, String likeCheck) throws Exception {
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(seqno).get();
		MemberEntity memberEntity = memberRepository.findById(userid).get();	
			
		LikeEntity likeEntity = likeRepository.findBySeqnoAndUserid(freeBoardEntity, memberEntity);
		//LikeEntity likeEntity = likeRepository.findBySeqnoAndUserid(seqno, userid);
		likeEntity.setLikeCheck(likeCheck);
		likeRepository.save(likeEntity);	
	}
	
	//좋아요/싫어요 갯수 수정하기 --> tbl_board내의 likecnt, dislikecnt 값을 변경
	@Override
	public void boardLikeUpdate(Long seqno, int likeCnt) throws Exception {
		
		FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(seqno).get();
		freeBoardEntity.setLikeCnt(likeCnt);
		freeBoardRepository.save(freeBoardEntity);
	}
	
	//댓글 보기
	@Override
	public List<ReplyInterface> replyView(ReplyInterface reply) throws Exception {
		return replyRepository.replyView(reply.getSeqno());
	}
	
	//댓글 수정
	@Override
	public void replyUpdate(ReplyInterface reply) throws Exception {
		ReplyEntity replyEntity = replyRepository.findById(reply.getReplySeqno()).get();
		replyEntity.setReplyContent(reply.getReplyContent());
		replyRepository.save(replyEntity);
	}
	
	//댓글 등록 
	@Override
	public void replyRegistry(ReplyInterface reply) throws Exception {
		//FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(reply.getSeqno()).get();
		//MemberEntity memberEntity = memberRepository.findById(reply.getUserid()).get();
		//MemberEntity memberEntity = memberRepository.findByAvatar(reply.getAvatar());
		System.out.println("아바타 = " + reply.getAvatar()); 
				
		ReplyEntity replyEntity = ReplyEntity.builder()
									.seqno(reply.getSeqno())
									.userid(reply.getUserid())
									.replyWriter(reply.getReplyWriter())
									.replyContent(reply.getReplyContent())
									.replyRegdate(LocalDateTime.now())
									.avatar(reply.getAvatar())
									.build();
		replyRepository.save(replyEntity);
	}
	
	//댓글 삭제
	@Override
	public void replyDelete(ReplyInterface reply) throws Exception {
		ReplyEntity replyEntity = replyRepository.findById(reply.getReplySeqno()).get();
		replyRepository.delete(replyEntity);
	}

}
