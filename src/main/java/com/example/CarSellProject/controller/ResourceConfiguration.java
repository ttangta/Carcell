package com.example.CarSellProject.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 파일 저장 경로 등록
@Configuration
public class ResourceConfiguration implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/car/**")
		.addResourceLocations("file:///D:/leehyeok/spring_boot/workspace/static/car/");
		
	}
	
}
