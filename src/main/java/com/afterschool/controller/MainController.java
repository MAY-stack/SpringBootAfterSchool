package com.afterschool.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.afterschool.entity.ClassEntity;
import com.afterschool.entity.FreeBoardEntity;
import com.afterschool.entity.repository.ClassRepository;
import com.afterschool.entity.repository.FreeBoardRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final FreeBoardRepository freeBoardRepository;
	private final ClassRepository classRepository;
	
	//개발중 페이지 바로가기 링크 모음페이지
	@GetMapping("/link")
	public void getLink() throws Exception{
		
	}
	
	//채팅 페이지
	@GetMapping("/chat")
	public void getChat() throws Exception {
		
	}
	

	//메인 페이지
	@GetMapping("/main")
	public void getMain(HttpSession session, Model model) throws Exception{		
		
		//인기 강좌 정보
		ClassEntity topClassEntity = classRepository.getOneTopClass();
		model.addAttribute("topclass", topClassEntity);
		
		//자유게시판 최근글 정보 5개
		List<FreeBoardEntity> FreeBoardList = freeBoardRepository.getRecent5FreeBoard();
		
		for(int i = 0; i< FreeBoardList.size(); i++) {
			System.out.println(FreeBoardList.get(i).getTitle());
		}
		
		model.addAttribute("list", FreeBoardList);
		
	}
	
}
