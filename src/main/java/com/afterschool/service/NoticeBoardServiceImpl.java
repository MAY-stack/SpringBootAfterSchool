package com.afterschool.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.afterschool.dto.FileDTO;
import com.afterschool.dto.NoticeBoardDTO;
import com.afterschool.entity.FileEntity;
import com.afterschool.entity.NoticeBoardEntity;
import com.afterschool.entity.repository.FileRepository;
import com.afterschool.entity.repository.NoticeBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService{
	
	private final NoticeBoardRepository noticeBoardRepository;
	private final FileRepository fileRepository;
	
	//게시물 목록 보기
	@Override
	public Page<NoticeBoardEntity> list(int pageNum,int postNum, String classCode){
		PageRequest pageRequest = PageRequest.of(pageNum-1, postNum, Sort.by(Direction.DESC,"noticeSeqno"));
		return noticeBoardRepository.findByClassCode(classCode, pageRequest);
	}
	
	//게시물 번호 구하기
	@Override
	public Long getSeqnoWithNextval() {
		return noticeBoardRepository.getSeqnoWithNextval();
	}
	
	//게시물 등록
	@Override
	public void write(NoticeBoardDTO noticeboard) {
		noticeboard.setNoticeRegdate(LocalDateTime.now());
		noticeBoardRepository.save(noticeboard.dtoToEntity(noticeboard));
	}
	
	//파일 업로드 정보 등록
	@Override
	public void fileInfoRegistry(FileDTO fileDTO) throws Exception{
		fileDTO.setNoticeSeqno(getSeqnoWithNextval());
		fileRepository.save(fileDTO.dtoToEntity(fileDTO));
	}
	
	//게시물 상세 보기
	@Override
	public NoticeBoardDTO view(Long noticeSeqno) {
		return noticeBoardRepository.findById(noticeSeqno).map(view-> new NoticeBoardDTO(view)).get();
	}
	
	//다운로드를 위한 파일 정보 보기
	@Override
	public FileDTO fileInfo(Long fileSeqno) throws Exception{
		return fileRepository.findById(fileSeqno).map(file->new FileDTO(file)).get();
	}
	
	//게시글 내에서 업로드된 파일 목록 보기
	@Override
	public List<FileEntity> fileListView(Long noticeSeqno) throws Exception{
		return fileRepository.findByNoticeSeqnoAndCheckfile(noticeSeqno, "Y");
	}
	
	//이전 보기
	@Override
	public Long pre_seqno(Long noticeSeqno, String classCode) {
		return noticeBoardRepository.findPreSeqno(noticeSeqno, classCode);
	}
	
	//다음 보기
	@Override
	public Long next_seqno(Long noticeSeqno, String classCode) {
		return noticeBoardRepository.findNextSeqno(noticeSeqno, classCode);
	}
	
	//조회수 업데이트
	@Override
	public void hitno(NoticeBoardDTO noticeboard) {
		noticeBoardRepository.updateHitno(noticeboard.getNoticeSeqno());
	}
	
	//게시물 수정 
	@Override
	public void modify(NoticeBoardDTO noticeboard) {		
		NoticeBoardEntity noticeboardEntity = noticeBoardRepository.findById(noticeboard.getNoticeSeqno()).get();
		noticeboardEntity.setNoticeTitle(noticeboard.getNoticeTitle());
		noticeboardEntity.setNoticeContent(noticeboard.getNoticeContent());		
		noticeBoardRepository.save(noticeboardEntity);
	}
	
	//게시물 수정에서 파일 삭제--> tbl_file내의 checkfile을 "N"으로 변환
	@Override
	public void deleteFileList(Long fileSeqno,String kind) throws Exception{
		if(kind.equals("F")) {
			FileEntity fileEntity = fileRepository.findById(fileSeqno).get();
			fileEntity.setCheckfile("N");
			fileRepository.save(fileEntity);
			} else if(kind.equals("B")) {
				fileRepository.findByNoticeSeqno(fileSeqno).stream().forEach(file-> {
			 	file.setCheckfile("N");
			 	fileRepository.save(file);
			 	});
		 	}
	}
	
	//게시물 삭제
	@Override
	public void delete(Long noticeSeqno, String classCode) {
//		NoticeBoardEntity noticeboardEntity = noticeBoardRepository.findById(noticeSeqno).get();
//		noticeBoardRepository.delete(noticeboardEntity);
		NoticeBoardEntity noticeboardEntity = noticeBoardRepository.findByNoticeSeqnoAndClassCode(noticeSeqno, classCode);
		noticeBoardRepository.delete(noticeboardEntity);
		
		System.out.println("classCode = = = =  = = =" + classCode);
		System.out.println("noticeSeqno = = = = = = =" + noticeSeqno);
	}
}
