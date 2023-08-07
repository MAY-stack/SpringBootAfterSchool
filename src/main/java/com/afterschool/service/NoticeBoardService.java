package com.afterschool.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.afterschool.dto.FileDTO;
import com.afterschool.dto.NoticeBoardDTO;
import com.afterschool.entity.FileEntity;
import com.afterschool.entity.NoticeBoardEntity;



public interface NoticeBoardService {
	
	//게시물 목록 보기
	public Page<NoticeBoardEntity> list(int startPoint,int postNum, String classCode);
	
	//게시물 번호 구하기
	public Long getSeqnoWithNextval();
	
	//게시물 등록
	public void write(NoticeBoardDTO noticeboard);
	
	//파일 업로드 정보 등록
	public void fileInfoRegistry(FileDTO fileDTO) throws Exception;
	
	//게시물 상세 보기
	public NoticeBoardDTO view(Long noticeSeqno);
	
	//다운로드를 위한 파일 정보 보기
	public FileDTO fileInfo(Long fileSeqno) throws Exception;
	
	//게시글 내에서 업로드된 파일 목록 보기
	public List<FileEntity> fileListView(Long noticeSeqno) throws Exception;
	
	//이전 보기
	public Long pre_seqno(Long noticeSeqno, String classCode);
	
	//다음 보기
	public Long next_seqno(Long noticeSeqno, String classCode);
	
	//조회수 업데이트
	public void hitno(NoticeBoardDTO noticeboard);
	
	//게시물 수정 
	public void modify(NoticeBoardDTO noticeboard);
	
	//게시물 수정에서 파일 삭제
	public void deleteFileList(Long fileSeqno,String kind) throws Exception;
	
	//게시글 삭제
	public void delete(Long noticeSeqno, String classCode);

}
