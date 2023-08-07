package com.afterschool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.afterschool.service.MemberUserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final AuthSuccessHandler authSuccessHandler;
	private final AuthFailureHandler authFailureHandler;
	private final MemberUserDetailsServiceImpl userDetailsService;

	//스프링시큐리티 암호화 스프링빈 등록
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//스프링시큐리티 적용 제외 대상 설정 스프링빈 등록
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		
		return (web)->web.ignoring().requestMatchers("/static/**","/css/**","/profile/**", "/headerAndFooter", "/main/**");
	}

	//로그인 처리
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .formLogin(login -> login
                    .usernameParameter("userid")   
                    .loginPage("/account/login")
                    .successHandler(authSuccessHandler)
                    .failureHandler(authFailureHandler));       
       
        http
            .rememberMe(me -> me
                    .key("afterschool")
                    .alwaysRemember(false) 
                    .tokenValiditySeconds(3600 * 24 * 7)
                    .rememberMeParameter("remember-me")
                    .userDetailsService(userDetailsService)
                    .authenticationSuccessHandler(authSuccessHandler));

		//접근권한 설정(권한 부여 : Authorization)
		http
		.authorizeHttpRequests()
		.requestMatchers("/freeboard/**").hasAnyAuthority("STUDENT","MANAGER")
		.requestMatchers("/noticeboard/**").hasAnyAuthority("STUDENT","TEACHER","MANAGER")
		.requestMatchers("/noticeboard/noticeWrite/**").hasAnyAuthority("TEACHER","MANAGER")
		.requestMatchers("/manager/**").hasAnyAuthority("MANAGER")
		.requestMatchers("/student/**").hasAnyAuthority("STUDENT")
		.requestMatchers("/teacher/**").hasAnyAuthority("TEACHER")
		.requestMatchers("/**").permitAll()
		.anyRequest().authenticated();
		
		//세션 설정
        http
            .sessionManagement(management -> management
                    .maximumSessions(1) 
                    .maxSessionsPreventsLogin(false) 
                    .expiredUrl("/account/login"));
        
        //로그 아웃
        http
            .logout(logout -> logout
                    .logoutUrl("/account/logout") 
                    .logoutSuccessUrl("/main") 
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .permitAll());
//        
        //CSRF, CORS 공격 보안 비활성화
        http
           .csrf((csrf) -> csrf.disable())
           .cors((cors) -> cors.disable());
		
		System.out.println("************* 스프링 시큐리티 설정 완료 !!! *************");
		
		return http.build();
	}
}
