package com.afterschool.controller;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afterschool.dto.FreeBoardDTO;
import com.afterschool.dto.ReplyInterface;
import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.LikeEntity;
import com.afterschool.service.FreeBoardService;
import com.afterschool.util.PageUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FreeBoardController {
	
	private final FreeBoardService service; //의존성 주입

	//글목록보기
	@GetMapping("/freeboard/freeList")
	public void getList(@RequestParam("page") int pageNum, @RequestParam(name="keyword",defaultValue="",required=false) String keyword, Model model) throws Exception{
		
		int postNum = 10; //한 화면에 보여지는 게시물 행의 갯수
		int pageListCount = 10; //화면 하단에 보여지는 페이지리스트 내의 페이지 갯수
		
		PageUtil page = new PageUtil();
		
		Page<FreeBoardEntity> list = service.list(pageNum, postNum, keyword);
		
		model.addAttribute("list",list);
		model.addAttribute("page", pageNum);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageList", page.getPageList(pageNum, postNum, pageListCount, (int)list.getTotalElements(), keyword));	
	}
	
	//글쓰기화면 보기
	@GetMapping("/freeboard/freeWrite")
	public void getWrite() throws Exception {
			
	}
		
	//글쓰기 처리
	@ResponseBody
	@PostMapping("/freeboard/freeWrite")
	public String postWrite(FreeBoardDTO freeboard, HttpSession session) throws Exception {
		//Long seqno = service.getSeqnoWithNextval();
		//freeboard.setSeqno(seqno);
		freeboard.setAvatar((String)session.getAttribute("avatar"));
		service.write(freeboard);
		
		return "{\"message\":\"good\"}";
	}
	
	//글상세보기
	@GetMapping("/freeboard/freeView")
	public void getView(@RequestParam("seqno") Long seqno, @RequestParam("page") int pageNum, @RequestParam(name="keyword", required=false) String keyword,
			Model model, HttpSession session) throws Exception {
		
		String SessionId = (String)session.getAttribute("userid");
		FreeBoardDTO view = service.view(seqno);
		
		//조회수 증가
		if(!SessionId.equals(view.getUserid())) {
			service.hitno(view);
		}
		
		//좋아요, 싫어요 처리
		LikeEntity myLikeCheck = service.likeCheckView(seqno, SessionId);
		
		//초기에 좋아요/싫어요 등록이 안되어져 있을 경우 "N"으로 초기화		
		if(myLikeCheck != null) {
			model.addAttribute("myLikeCheck", myLikeCheck.getLikeCheck());
		} else {
			model.addAttribute("myLikeCheck", "N");
		}
		
		model.addAttribute("view", view);
		model.addAttribute("viewId", view.getUserid());
		model.addAttribute("page", pageNum);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pre_seqno", service.pre_seqno(seqno, keyword));
		model.addAttribute("next_seqno", service.next_seqno(seqno, keyword));
	}
	
	//게시물 수정 보기
	@GetMapping("/freeboard/freeModify")
	public void getModify(@RequestParam("seqno") Long seqno, @RequestParam("page") int pageNum, Model model,
			@RequestParam(name="keyword",required=false) String keyword) throws Exception{
		
		
		
		model.addAttribute("view", service.view(seqno));
		model.addAttribute("ViewUserID", service.view(seqno).getUserid());
		model.addAttribute("page", pageNum);
		model.addAttribute("keyword", keyword);
		
	}
	
	//게시물 수정 처리
	@ResponseBody
	@PostMapping("/freeboard/freeModify")
	public String postModify(FreeBoardDTO freeboard, @RequestParam("page") int pageNum,
			@RequestParam(name="keyword", required=false) String keyword) throws Exception {
	
		System.out.println("page = " + pageNum);
		
		service.modify(freeboard);
		
		return "{\"message\":\"good\"}";
	}
	
	//게시물 삭제 처리
	@GetMapping("/freeboard/freeDelete")
	public String getDelete(@RequestParam("seqno") Long seqno) throws Exception {
		service.delete(seqno);
		
		return "redirect:/freeboard/freeList?page=1";
	}
	
	//좋아요 관리
	@ResponseBody
	@PostMapping(value = "/freeboard/likeCheck")
	public String postLikeCheck(@RequestBody Map<String, Object> likeCheckData) throws Exception {
		
		
		int seq = (int)likeCheckData.get("seqno");
		Long seqno = (long)seq;
		String userid = (String)likeCheckData.get("userid");
		String likeCheck = (String)likeCheckData.get("mylikecheck");		
		//int checkCnt = (int)likeCheckData.get("checkCnt");
		
		System.out.println("likeCheck = " + likeCheck);
		
		//내가 체크한 정보 가져오기
		LikeEntity likeCheckView = service.likeCheckView(seqno, userid);
		
		if(likeCheckView == null) {
			service.likeCheckRegistry(seqno,userid,likeCheck);
		} else {
			service.likeCheckUpdate(seqno,userid,likeCheck);
		}

		//TBL_BOARD 내의 likecnt 입력/수정 
		FreeBoardDTO freeBoardDTO = service.view(seqno);
		
		int likeCnt = freeBoardDTO.getLikeCnt();
		
		if(likeCheck.equals("Y")) {
			likeCnt ++;
		} else {
			likeCnt --;
		}

		service.boardLikeUpdate(seqno, likeCnt);

		return "{\"likeCnt\":\"" + likeCnt + "\"}";
	}
	
	//댓글 처리
	@ResponseBody
	@PostMapping("/freeboard/reply")
	public List<ReplyInterface> postReply(ReplyInterface reply,@RequestParam("option") String option) throws Exception {
		switch(option) {
			case "I" : service.replyRegistry(reply); //댓글 등록
					break;
			case "U" : service.replyUpdate(reply); //댓글 수정
				   break;
			case "D" : service.replyDelete(reply); //댓글 삭제
				   break;
		}
		
		return service.replyView(reply);
		
	}
	
		
}
