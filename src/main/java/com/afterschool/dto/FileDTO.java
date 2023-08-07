package com.afterschool.dto;

import com.afterschool.entity.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
	
	private Long fileSeqno;
	private Long noticeSeqno;
//  FK
//  private NoticeBoardEntity noticeSeqno;
	private String userid;
//  FK
//  private MemberEntity userid;
	private String orgFilename;
	private String storedFilename;
	private Long filesize;
	private String checkfile;
	
	public FileDTO(FileEntity fileEntity) {
		
		this.fileSeqno = fileEntity.getFileSeqno();
		this.noticeSeqno = fileEntity.getNoticeSeqno();
		this.userid = fileEntity.getUserid();
		this.orgFilename = fileEntity.getOrgFilename();
		this.storedFilename = fileEntity.getStoredFilename();
		this.filesize = fileEntity.getFilesize();
		this.checkfile = fileEntity.getCheckfile();		
		
	}
	
	public FileEntity dtoToEntity(FileDTO dto) {
		
		FileEntity fileEntity = FileEntity.builder()
							.fileSeqno(dto.getFileSeqno())
							.noticeSeqno(dto.getNoticeSeqno())
							.userid(dto.getUserid())
							.orgFilename(dto.getOrgFilename())
							.storedFilename(dto.getStoredFilename())
							.filesize(dto.getFilesize())
							.checkfile(dto.getCheckfile())
							.build();
		return fileEntity;
	}
	
}
