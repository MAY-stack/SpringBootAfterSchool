package com.afterschool.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.afterschool.entity.MemberEntity;
import com.afterschool.entity.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsServiceImpl implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberEntity memberInfo = memberRepository.findById(username).get(); //username --> 아이디			

		if(memberInfo == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}
		
//		memberInfo.orElseThrow(()-> new UsernameNotFoundException("아이디가 존재하지 않습니다."));	
//		Optional<MemberEntity> memberInfo = memberRepository.findById(username); //username --> 아이디
		
		//SimpleGrantedAuthority : 여러개의 사용자 Role값을 받아서 권한을 인식하는 역할을 함
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();		
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(memberInfo.getRole());
		grantedAuthorities.add(grantedAuthority);
		
		//User 생성자 인자 --> 아이디, 패스워드, 사용자롤들
		User user = new User(username, memberInfo.getPassword(), grantedAuthorities);
		
		return user;
	
	}
}
