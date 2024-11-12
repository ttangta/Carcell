package com.example.CarSellProject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CarSellProject.dao.AdminControllerDAO;
import com.example.CarSellProject.dao.CarDAO;
import com.example.CarSellProject.dao.FuelDAO;
import com.example.CarSellProject.dao.ShopDAO;
import com.example.CarSellProject.dto.AdminControllerDTO;
import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Fuel;
import com.example.CarSellProject.entity.Shop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarController {
	
	@Autowired
	CarDAO Cdao;
	
	@Autowired
	ShopDAO Sdao;
	
	@Autowired
	FuelDAO Fdao;
	
	@Autowired
	AdminControllerDAO Adao;
	
	
	// 상세보기
	@GetMapping("/carView")
	public String CarView(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		// 세션에서 "Userid" 속성 가져오기
		String userId = (String) session.getAttribute("Userid");
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		if(userId != null) {
		
		
		System.out.println("carnum" + carnum);
		
		Car car = Cdao.getCarInfo(carnum);
		
		System.out.println("carnum" + carnum);
		// 전부? 가져오기
		List<Shop> shopList = Sdao.getShop(carnum);
		System.out.println("shopList");
		// 지역 가져오기
		List<Shop> location = Sdao.getLocation(carnum);
		
		
		// 연료 가저오기
		List<Fuel> fuel  = Fdao.fuelCarNum(carnum);
		System.out.println("fuel : " + fuel);
		
		
		if(car.getCondition().equals("old")) {
			Shop shop = Sdao.getOldCarShop(carnum);
			model.addAttribute("shop",shop);
		}
		
		
		System.out.println("location " + location);
		System.out.println("car " + car);
		System.out.println("shopList " + shopList);

		model.addAttribute("car",car);
		model.addAttribute("fuel",fuel);
		model.addAttribute("carnum",carnum);
		model.addAttribute("location",location);
		model.addAttribute("shopList",shopList);
		
		return "car/carView";
		} else{
			return "/member/goLogin";
		}
		
	}
	
	// 판매점 이름 가저오기
	@ResponseBody
	@GetMapping("/shopAdd")
    public Map<String, Object> getOptions(@RequestParam("shoploca") String shoploca,
    										@RequestParam("carnum") String carnum) {
		int carnum1 = Integer.parseInt(carnum);
	    List<String> options = Sdao.getShopName(carnum1, shoploca); // DB에서 옵션 가져오기
	    System.out.println("options : " + options);
	    // 응답 데이터 생성
	    Map<String, Object> response = new HashMap<>();
	    response.put("options", options);

	    return response;
    }
	
	
	
	@PostMapping("/carReservation")
	public String CarReservation(Model model, HttpServletRequest request) {
		
		
		
		// 차 상태?가져오기
		String condition = request.getParameter("condition");
		System.out.println("condition입니다. : " +condition);
		
		String shopname = "";
		if(condition.equals("new")) {
			//판매점 이름 가저오기
			shopname = request.getParameter("addressOptions");
			System.out.println("new-shopname : " + shopname);
		} else {
			shopname = request.getParameter("shopname");
			System.out.println("old-shopname : " + shopname);
		}
		
	
		
		// 차 번호 가저오기
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		// 사용자 아이디 가저오기
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("Userid");
		String userName = (String)session.getAttribute("UserName");
		
		System.out.println("condition : " + condition);
		
		// 차량상태 판매 예약중으로 변경
		if(condition.equals("old")) {
			boolean result = Cdao.updateCondition(carnum);
			System.out.println("result : "  +result);
		}
		
		
		
		System.out.println(userId);
		System.out.println(carnum);
		System.out.println(shopname);
		
		// 상점이름으로 상점 식별코드 가저오기
		int shopid = Sdao.getShopId(shopname);
		
		// 상점 정보 가져오기
		Shop shop = Sdao.ShopInfo(shopid);
		// 차량 정보 가져오기
		Car car = Cdao.getCarInfo(carnum);
		
		// 구매에서 넘어온거임
		String type= "구매";
		
		// date를 string으로 변경
		// 현재 날짜와 시간
        Date currentDate = new Date();

        // 원하는 형식으로 포맷 설정 (예: "yyyy-MM-dd HH:mm:ss")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Date를 String으로 변환
        String formattedDate = dateFormat.format(currentDate);
        
		
		
		AdminControllerDTO dto = new AdminControllerDTO();
		dto.setUserid(userId);		// 사용자아이디   0
		dto.setShopid(shopid);		// 판매점아이디   0 
		dto.setCarnum(carnum);		// 차량 아이디    0
		dto.setTime(formattedDate);	// 현시간 저장
		dto.setType(type);			// 목적?
		dto.setStatus(0);			// 상태 : 0 -> 미해결, 1 -> 해결  이것도 필요없나 해결하면 삭제할까 고민
		
		boolean result = Adao.userSell(dto);
		
		
		
		model.addAttribute("userId",userId);
		model.addAttribute("userName",userName);
		model.addAttribute("shop",shop);
		model.addAttribute("car",car);
		model.addAttribute("type",type);
		model.addAttribute("result",result);
		
		
		
		return "car/carReservation";
		
	}
	

}
