package com.example.CarSellProject.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.CarSellProject.dao.CarDAO;
import com.example.CarSellProject.entity.Car;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class carSearchController {
	@Autowired
	CarDAO dao;
	
	
	@GetMapping("/search_car")
	public String carListSearch(Model model, HttpServletRequest request) {
		
	   
	   int pg = 1;
		if(request.getParameter("pg") !=null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		
		System.out.println("pg" + pg);
		// 목록은 한 화면에 5개씩
		int endNum = pg*5;
		int startNum = endNum-4;
		String search = null;
		if(request.getParameter("search") != null || !request.getParameter("search").equals("")) {
			search = "%" +request.getParameter("search") + "%";
		}
		List<Car> list = dao.search(search,startNum,endNum);
		String re_search = search.replaceAll("%", "");
				
		System.out.println("re_search : " + re_search);
		System.out.println("list : " + list);
		System.out.println("pg :" + pg);
		// 총개수 구하기
		int total = dao.searchCount(search); // 총 글수;
		
		
		int totalPage = (total+4)/5;  // 총 페이지 수
		
		int startPage = (pg-1)/3*3+1;
		int endPage = startPage + 2;
		if (endPage > totalPage) endPage = totalPage;
		
		System.out.println("total :" + total);
		System.out.println("totalPage :" + totalPage);
		System.out.println("startPage :" + startPage);
		System.out.println("endPage :" + endPage);
		System.out.println("pg :" + pg);
		System.out.println("list :" + list.size());

		
		// 2. 데이터 공유
		
		model.addAttribute("re_search", re_search);
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("total", total);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		// 로그인 정보 공유
		HttpSession session = request.getSession();
		model.addAttribute("userId",session.getAttribute("id"));
		
		
		return "car/search_carList";
	   
	}
	
	@GetMapping("/car/sellMyCar")
	public String sellMyCar(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		String Userid = (String)session.getAttribute("Userid");
		System.out.println("Userid : " + Userid);
		model.addAttribute("Userid", Userid);
		return "car/sellMyCar";
	}

	@GetMapping("/car/sellMyCarForm")
	public String sellMyCarForm() {
		return "/car/sellMyCarForm";
	}
	@GetMapping("/car/newRegisterCar")
	public String newRegisterCar(Model model) {
		model.addAttribute("req_page", "/car/newRegisterCar");
		return "/member/adminView";
	}
	
}
