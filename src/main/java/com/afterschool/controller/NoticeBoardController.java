package com.afterschool.controller;


import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.afterschool.dto.FileDTO;
import com.afterschool.dto.NoticeBoardDTO;
import com.afterschool.entity.NoticeBoardEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.service.NoticeBoardService;
import com.afterschool.util.PageUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeBoardController {
	
	private final NoticeBoardService service; //의존성 주입
	private final ClassRepository classRepository; //의존성 주입

	//글목록보기
	@GetMapping("/noticeboard/noticeList")
	public void getNoticeList(NoticeBoardDTO noticeboard,@RequestParam("classCode") String classCode,
			@RequestParam("page") int pageNum, Model model) throws Exception{
		
		int postNum = 10; //한 화면에 보여지는 게시물 행의 갯수
		int pageListCount = 10; //화면 하단에 보여지는 페이지리스트 내의 페이지 갯수
		
		PageUtil page = new PageUtil();
		
		Page<NoticeBoardEntity> list = service.list(pageNum, postNum, classCode);
		System.out.println("class code is " + classCode);
		
		model.addAttribute("list",list);
		model.addAttribute("page", pageNum);
		model.addAttribute("classCode", classCode);
		model.addAttribute("className", classRepository.findByClassCode(classCode).getClassName());
		model.addAttribute("pageList", page.getPageList(pageNum, postNum, pageListCount, (int)list.getTotalElements(), classCode));	
	}
	
	//글쓰기화면 보기
	@GetMapping("/noticeboard/noticeWrite")
	public void getNoticeWrite(@RequestParam("classCode") String classCode, Model model) throws Exception {
		model.addAttribute("classCode", classCode);
	}
	
	//첨부 파일 없는 게시물 등록
	@ResponseBody
	@PostMapping("/noticeboard/noticeWrite")
	public String PostWrite(NoticeBoardDTO noticeboard, @RequestParam("classCode") String classCode, HttpSession session) throws Exception{

		//Long seqno = service.getSeqnoWithNextval();
		//noticeboard.setNoticeSeqno(seqno);
		noticeboard.setStoredProfilename((String)session.getAttribute("profile"));
		service.write(noticeboard);
		return "{\"message\":\"good\"}";

	}
	
	//파일 업로드
	@ResponseBody
	@PostMapping("/noticeboard/fileUpload")
	public String postFileUpload(NoticeBoardDTO noticeboard,FileDTO file,@RequestParam("SendToFileList") List<MultipartFile> multipartFile, 
			@RequestParam("kind") String kind,@RequestParam(name="deleteFileList", required=false) Long[] deleteFileList, HttpSession session) throws Exception{

		String path = "c:\\Repository\\file\\"; 
		Long seqno =0L;
		
		if(kind.equals("I")) { //게시물 등록 시 게시물 등록 
			//seqno = service.getSeqnoWithNextval();
			//file.setNoticeSeqno(seqno);
			noticeboard.setStoredProfilename((String)session.getAttribute("profile"));
			service.write(noticeboard);
			
			System.out.println("seqno =========================== " + noticeboard.getNoticeSeqno());
			file.setNoticeSeqno(noticeboard.getNoticeSeqno());
		}
		
		if(kind.equals("U")) { //게시물 수정 시 게시물 수정
			seqno = noticeboard.getNoticeSeqno();
			service.modify(noticeboard);
			
			if(deleteFileList != null) {
				
				for(int i=0; i<deleteFileList.length; i++) {
					
					//파일 테이블에서 파일 정보 삭제
					//게시물 수정에서 삭제할 파일 목록이 전송되면 이 값을 받아서 tbl_file내에 있는 파일 정보를 하나씩 삭제하는 deleteFileList 실행

					service.deleteFileList(deleteFileList[i],"F"); 
					
				}
			}	
		}
		
		if(!multipartFile.isEmpty()) {//파일 등록 및 수정 시 파일 업로드
			File targetFile = null; 
			
			for(MultipartFile mpr:multipartFile) {
				
				String org_filename = mpr.getOriginalFilename();	
				String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
				String stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
				long filesize = mpr.getSize();

				targetFile = new File(path + stored_filename);
				mpr.transferTo(targetFile);
				
				String userid = (String) session.getAttribute("userid");
				
				FileDTO fileDTO = FileDTO.builder()
								.noticeSeqno(seqno)
								.userid(userid)
								.orgFilename(org_filename)
								.storedFilename(stored_filename)
								.filesize(filesize)
								.checkfile("Y")
								.build();
				
				service.fileInfoRegistry(fileDTO);
	
			}
			
		}
		
		return "{\"message\":\"good\"}";
	}

	//글상세보기
	//@RequestParam("noticeSeqno") Long noticeSeqno
	@GetMapping("/noticeboard/noticeView")
	public void getNoticeView(NoticeBoardDTO noticeboard, @RequestParam("classCode") String classCode, @RequestParam("noticeSeqno") Long noticeSeqno, @RequestParam("page") int pageNum,
			Model model, HttpSession session) throws Exception {
		
		System.out.println("noticeSeqno = " + noticeSeqno);
		
		String SessionId = (String)session.getAttribute("userid");
		NoticeBoardDTO view = service.view(noticeSeqno);
		//String classCode = noticeboard.getClassCode(); 
		
		//조회수 증가 처리
		if(!SessionId.equals(view.getUserid())) {
			service.hitno(view);
		}
		
		System.out.println("pre_Seqno = " + noticeSeqno);
		
		model.addAttribute("view", view);
		model.addAttribute("viewId", view.getUserid());
		model.addAttribute("page", pageNum);
		model.addAttribute("classCode", classCode);
		model.addAttribute("pre_seqno", service.pre_seqno(noticeSeqno, classCode));
		model.addAttribute("next_seqno", service.next_seqno(noticeSeqno, classCode));
		model.addAttribute("fileListView", service.fileListView(noticeSeqno));
		
	}
	
	//파일 다운로드
	@GetMapping("/noticeboard/filedownload")
	public void filedownload(@RequestParam("fileSeqno") Long fileSeqno, HttpServletResponse rs) throws Exception {
		
		String path = "c:\\Repository\\file\\";
		
		FileDTO fileInfo = service.fileInfo(fileSeqno);
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(path+fileInfo.getStoredFilename()));
		
		//헤드값을 Content-Disposition로 주게 되면 Response Body로 오는 값을 filename으로 다운받으라는 것임
		//예) Content-Disposition: attachment; filename="hello.jpg"
		rs.setContentType("application/octet-stream");
		rs.setContentLength(fileByte.length);
		rs.setHeader("Content-Disposition",  "attachment; filename=\""+URLEncoder.encode(fileInfo.getOrgFilename(), "UTF-8")+"\";");
		rs.getOutputStream().write(fileByte);
		rs.getOutputStream().flush();//버퍼에 있는 내용을 write
		rs.getOutputStream().close();
		
	}
	
	//수정화면보기
	@GetMapping("/noticeboard/noticeModify")
	public void getNoticeModify(@RequestParam("noticeSeqno") Long noticeSeqno, @RequestParam("page") int pageNum,
			@RequestParam("classCode") String classCode, Model model) throws Exception {
		
		model.addAttribute("view", service.view(noticeSeqno));
		model.addAttribute("ViewId", service.view(noticeSeqno).getUserid());
		model.addAttribute("page", pageNum);
		model.addAttribute("classCode", classCode);
		model.addAttribute("fileListView", service.fileListView(noticeSeqno));
	}
	
	//수정 처리
	@ResponseBody
	@PostMapping("/noticeboard/noticeModify")
	public String postNoticeModify(NoticeBoardDTO noticeboard, @RequestParam("page") int pageNum,
			@RequestParam("classCode") String classCode,
			@RequestParam(name="deleteFileList", required=false) Long[] deleteFileList) throws Exception {
		
		System.out.println("classCode = " + classCode);
		service.modify(noticeboard);
		
		if(deleteFileList != null) {
			for(int i = 0; i < deleteFileList.length; i++) {
				//파일 테이블에서 파일 정보 삭제
				service.deleteFileList(deleteFileList[i],"F");
			}
		}
		return "{\"message\":\"good\"}";
	}
	
	//글 삭제
	@GetMapping("/noticeboard/noticeDelete")
	//@RequestParam("classCode") String classCode,
	public String getNoticeDelete(@RequestParam("classCode") String classCode, @RequestParam("noticeSeqno") Long noticeSeqno, Model model) throws Exception {
		service.deleteFileList(noticeSeqno, "B");
		service.delete(noticeSeqno, classCode);
		
		System.out.println("classCode = " + classCode);
		System.out.println("noticeSeqno = " + noticeSeqno);
		
		//model.addAttribute("classCode", classCode);
		//model.addAttribute("noticeSeqno", noticeSeqno); 
		
		return "redirect:/noticeboard/noticeList?classCode=" + classCode + "&page=1";
	}
	
}
