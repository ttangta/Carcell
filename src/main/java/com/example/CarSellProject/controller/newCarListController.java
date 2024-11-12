package com.example.CarSellProject.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.service.CarService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/newcarList")
public class newCarListController {

	@Autowired
	CarService service;

	// 리스트 화면
	@GetMapping
	public String newcarList(Model model, HttpServletRequest request) {
		
		int minPrice = 0;
		int maxPrice = 0;
		
		int pg = 1;
		if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		// 목록은 한 화면에 5개씩
		int endNum = pg * 5;
		int startNum = endNum - 4;
		List<String> condition = Arrays.asList("new");
		List<Car> list = service.carListNew(startNum, endNum,condition);

		// 총개수 구하기
		int total = service.carTotal(); // 총 글수
		int totalPage = (total + 4) / 5; // 총 페이지 수

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalPage)
			endPage = totalPage;

		List<String> makers = new ArrayList<>();
		List<String> sizes = new ArrayList<>();
		List<String> fuels = new ArrayList<>();
		// 2. 데이터 공유

		model.addAttribute("makers", makers);
		model.addAttribute("sizes", sizes);
		model.addAttribute("fuels", fuels);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		
		
		model.addAttribute("condition", condition);
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		// 로그인 정보 공유
		HttpSession session = request.getSession();
		model.addAttribute("userId", session.getAttribute("id"));

		return "car/newCarList";
	}
	
	@PostMapping
	public String newcarListSearch(Model model, HttpServletRequest request) {
		
		List<String> condition = Arrays.asList("new");
		String makersNullCheck = null;
		String sizesNullCheck = null;
		String fuelsNullCheck = null;
		
		int minPrice = 0;
		if(request.getParameter("minPrice") !=null && !request.getParameter("minPrice").equals("")) {
			minPrice = Integer.parseInt(request.getParameter("minPrice"));
		}
		int maxPrice = 0;
		if(request.getParameter("maxPrice") !=null && !request.getParameter("maxPrice").equals("")) {
			maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
		}
		System.out.println("minPrice : " + minPrice);
		System.out.println("maxPrice : " + maxPrice);
	    
		
	   //체크된 제조사 리스트
	   String[] makerArray = request.getParameterValues("maker");
	   List<String> makers = makerArray != null ? Arrays.asList(makerArray) : new ArrayList<>();
	   if(makers.size()!=0) {
		   makersNullCheck = "1";
	   }
	   System.out.println("makers : " + makers);
	   
	   
	   // 체크된 자동차 크기 리스트
	   String[] sizeArray = request.getParameterValues("carsize");
	   List<String> sizes = sizeArray != null ? Arrays.asList(sizeArray) : new ArrayList<>();
	   if(sizes.size()!=0) {
		   sizesNullCheck = "1";
	   }
	   System.out.println("sizes : " + sizes);
	   
	   
	   // 체크된 연료 리스트
	   String[] fuelArray = request.getParameterValues("fuelType");
	   List<String> fuels = fuelArray != null ? Arrays.asList(fuelArray) : new ArrayList<>();
	   if(fuels.size()!=0) {
		   fuelsNullCheck = "1";
	   }
	   System.out.println("fuels : " + fuels);
	   
	   
	   int pg = 1;
		if(request.getParameter("pg") !=null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		
		// 목록은 한 화면에 5개씩
		int endNum = pg*5;
		int startNum = endNum-4;
		
		List<Car> list = service.hardsearch(minPrice, maxPrice, makers, sizes, fuels, startNum, endNum, condition,makersNullCheck, sizesNullCheck,fuelsNullCheck);
		
		System.out.println("list : " + list);
		
		// 총개수 구하기
		int total = service.newcarListCount(minPrice, maxPrice, makers, sizes, fuels, condition,makersNullCheck, sizesNullCheck,fuelsNullCheck); // 총 글수;
		System.out.println(total);
		
		int totalPage = (total+4)/5;  // 총 페이지 수
		
		int startPage = (pg-1)/3*3+1;
		int endPage = startPage + 2;
		if (endPage > totalPage) endPage = totalPage;
		
		// 2. 데이터 공유
		
		model.addAttribute("makers", makers);
		model.addAttribute("sizes", sizes);
		model.addAttribute("fuels", fuels);
		
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		
		model.addAttribute("condition", condition);
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		System.out.println("나도몰라");
		// 로그인 정보 공유
		HttpSession session = request.getSession();
		model.addAttribute("userId",session.getAttribute("id"));
		
		
		return "car/newCarList";
	   
	}
	
	
}
