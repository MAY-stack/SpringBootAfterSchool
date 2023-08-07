package com.afterschool.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterschool.entity.FileEntity;
public interface FileRepository extends JpaRepository<FileEntity,Long>{
	
	public List<FileEntity> findByNoticeSeqno(Long noticeseqno);
	public List<FileEntity> findByNoticeSeqnoAndCheckfile(Long noticeseqno, String checkfile);
	public List<FileEntity> findByCheckfile(String checkfile);

}
