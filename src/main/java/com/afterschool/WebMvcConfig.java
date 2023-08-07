package com.afterschool;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/profile/**")
				.addResourceLocations("file:///c:/Repository/profile/");
		registry.addResourceHandler("/classImage/**")
				.addResourceLocations("file:///c:/Repository/class/classImage/");
		registry.addResourceHandler("/lecture/**")
				.addResourceLocations("file:///c:/Repository/class/lectureVideos/");
	}
	
	
}
