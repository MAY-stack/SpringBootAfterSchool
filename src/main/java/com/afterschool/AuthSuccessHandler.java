package com.afterschool;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private final MemberRepository memberRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		MemberEntity memberInfo = memberRepository.findById(authentication.getName()).get();
		
		//세션 생성
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600*24*7);
		session.setAttribute("userid", memberInfo.getUserid());
		session.setAttribute("username", memberInfo.getUsername());
		session.setAttribute("role", memberInfo.getRole());
		if(memberInfo.getRole().equals("STUDENT")) {
			session.setAttribute("avatar", memberInfo.getAvatar());
			session.setAttribute("nickname", memberInfo.getNickname());
		}
		if(memberInfo.getRole().equals("TEACHER")) {
	         session.setAttribute("profile", memberInfo.getStoredProfilename());
		}
		System.out.println("*************** FormLogin 성공 !!! ***************");
		
		super.setDefaultTargetUrl("/main");
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
