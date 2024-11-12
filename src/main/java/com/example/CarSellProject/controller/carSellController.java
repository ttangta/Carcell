package com.example.CarSellProject.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.CarSellProject.dao.AdminControllerDAO;
import com.example.CarSellProject.dao.ShopDAO;
import com.example.CarSellProject.dto.AdminControllerDTO;
import com.example.CarSellProject.dto.AdmindetailDTO;
import com.example.CarSellProject.dto.CarDTO;
import com.example.CarSellProject.dto.FuelDTO;
import com.example.CarSellProject.dto.TotaltimeDTO;
import com.example.CarSellProject.entity.Admincontroller;
import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Fuel;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.entity.ShopCarId;
import com.example.CarSellProject.entity.Shopcar;
import com.example.CarSellProject.entity.Totaltime;
import com.example.CarSellProject.entity.Usermember;
import com.example.CarSellProject.model.StopWatch;
import com.example.CarSellProject.service.CarService;
import com.example.CarSellProject.service.FuelService;
import com.example.CarSellProject.service.ShopService;
import com.example.CarSellProject.service.TotalTimeService;
import com.example.CarSellProject.service.UsermemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class carSellController {
	// test 부분
	private StopWatch stopwatch = new StopWatch();
	
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	AdminControllerDAO ADCdao;
	
	@Autowired
	ShopService shopservice;
	
	@Autowired
	CarService service;
	
	@Autowired
	UsermemberService memberService;
	
	@Autowired
	FuelService fuelService;
	
	@Autowired
	TotalTimeService totaltimeService;
	
	//중고차 등록
	@PostMapping("/sellCar")
	public String sellCar(CarDTO dto,Model model,@RequestParam("img") MultipartFile uploadFile,HttpServletRequest request) {
		
		String[] fueltypes = request.getParameterValues("fuelType");
		
		if(dto.getMaker().equals("기타")) {
			String maker = request.getParameter("maker1");
			dto.setMaker(maker);
		}
		dto.setCardate(new Date());
		//dto.setBuyerid("null");
		// test 부분

		stopwatch.start();
		LocalDateTime startTime = stopwatch.getStartTime();
		System.out.println(startTime);

		// test 부분
		
		// 이미지 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setCarimage(fileName);
		// 이미지 저장
		if(!fileName.equals("")) {
			File file = new File(uploadpath,fileName);
			// 파일 저장
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Car sellcar = service.sellCar(dto);
		
		int cn = sellcar.getCarnum();
		
		System.out.println("fueltypes : " + fueltypes);
		// 연료 저장
	    if (fueltypes != null) {
	        for (String fueltype : fueltypes) {
	            FuelDTO fudto = new FuelDTO();
	            fudto.setCarnum(cn);
	            fudto.setFueltype(fueltype); // 단일 값으로 설정
		        System.out.println(fudto);
	            Fuel fuel = fuelService.fuelSave(fudto);
	            System.out.println("fuel : " + fuel);
	        }
	    }
		
		//////////////////////////////////////////
		// 판매점 저장하기
		// 필요한거 shopid (판매점코드), carnum
		// 선택한 판매점
		
		String shopname = request.getParameter("pointSelect");
		System.out.println("shopname : " + shopname);
		
		// 판매점 코드
		int shopid = shopservice.getShopId(shopname);
		
		Shop shop = new Shop();
	    shop.setShopid(shopid); // Shop 객체의 shopid 설정 (기타 필요한 필드도 설정)

	    Car car = new Car();
	    car.setCarnum(cn); // Car 객체의 carnum 설정 (기타 필요한 필드도 설정)

	    // ShopCarId 생성
	    ShopCarId shopCarId = new ShopCarId();
	    shopCarId.setShopid(shopid);
	    shopCarId.setCarnum(cn);

	    // ShopCar 객체 생성
	    Shopcar shopCar = new Shopcar();
	    shopCar.setId(shopCarId);
	    shopCar.setShop(shop); // Shop 객체 설정
	    shopCar.setCar(car); // Car 객체 설정
		
	    Shop shop2 = shopservice.ShopInfo(shopid);
	    Car car2 = service.getCarInfo(cn);
		// 연료 가저오기
		List<Fuel> fuel  = fuelService.fuelCarNum(cn);
		System.out.println("fuel : " + fuel);
	    
		// db 저장
	    shopservice.saveShopCar(shopCar);
	    model.addAttribute("shop", shop2);
	    model.addAttribute("fuel",fuel);
	    model.addAttribute("car", car2);
		/////////////////////////////
		// 판매자 정보
		String id = sellcar.getSellerid();
		Usermember user = memberService.searchUser1(id);
		
		//으아악
		String type = "판매";
		System.out.println(shopid);
	    System.out.println(cn);
	    System.out.println(id);
		
	    // date를 string으로 변경
	 	// 현재 날짜와 시간
	    Date currentDate = new Date();

	    // 원하는 형식으로 포맷 설정 (예: "yyyy-MM-dd HH:mm:ss")
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         
	    // Date를 String으로 변환
	    String formattedDate = dateFormat.format(currentDate);
	        
	 	
	 	
	 	AdminControllerDTO ACdto = new AdminControllerDTO();
	 	ACdto.setUserid(id);		// 사용자아이디   0
	 	ACdto.setShopid(shopid);		// 판매점아이디   0 
	 	ACdto.setCarnum(cn);		// 차량 아이디    0
	 	ACdto.setTime(formattedDate);	// 현시간 저장
	 	ACdto.setType(type);			// 목적?
	 	ACdto.setStatus(0);			// 상태 : 0 -> 미해결, 1 -> 해결  이것도 필요없나 해결하면 삭제할까 고민
	 		
	 	boolean result = ADCdao.userSell(ACdto);
	    
	    System.out.println("결과 : " + result);
		
		
		model.addAttribute("user", user);
		return "/car/sellCar";
	}
	// test
	@GetMapping("/stop")
	public String stop() {
		System.out.println("stopwatch 테스트");
		stopwatch.stop();
		LocalDateTime stopTime = stopwatch.getStopTime();
		System.out.println(stopTime);
		String totalTime = stopwatch.getElapsedTime();
		System.out.println("총 소요시간 : " + totalTime);
		return null;
	}
	
	// 중고차 감정리스트
	@GetMapping("/car/registerCar")
	public String registerCar(Model model, HttpServletRequest request) {
		int pg = 1;
		if(request.getParameter("pg") !=null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 해결여부 해결 = 1, 미해결 = 0
		int status = 0;
		if(request.getParameter("status") !=null && !request.getParameter("status").equals("")) {
			status = Integer.parseInt(request.getParameter("status"));
		}
		
		System.out.println("status : " + status);
		
		// 목록 5개
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		//요청 타입
		String type= "판매";
		
		// adc 테이블에서 구매요청,미해결 목록 가져오기 ad테이블에서 상점이름, 차량이름,이미지 추가
		List<AdmindetailDTO> ADlist = ADCdao.test1(startNum,type,status);
		
		// adc 테이블에서 구매요청,미해결 목록 가져오기			
		int totalList = ADCdao.totalCount(type,status);
		System.out.println("크기비교 : " + ADlist.size() + "+" + totalList);
		
		
		
		int totalPage = (totalList + 4) / 5;
		
		int startPage = (pg-1)/3*3+1;
		int endPage = startPage + 2;
		if(endPage > totalPage) endPage = totalPage;
		
		// 데이터 공유
		model.addAttribute("pg",pg );
		model.addAttribute("ADlist",ADlist);
		model.addAttribute("status",status);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("req_page", "/car/registerCar");
		
		return "/member/adminView";
	}
	
	@GetMapping("/car/carView")
	public String carView(HttpServletRequest request,Model model) {
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		System.out.println("carnum : " + carnum);
		System.out.println("pg : " + pg);
		Car car = service.carView(carnum);
		System.out.println(car);
		Usermember user = memberService.searchUser1(car.getSellerid());
		String userName = user.getName();
		model.addAttribute("car", car);
		model.addAttribute("userName", userName);
		return "car/carView";
	}
	
	@PostMapping("/resultPrice")
	public String resultPrice(HttpServletRequest request) {
		double accident = Double.parseDouble(request.getParameter("accident"));
		System.out.println(accident);
		System.out.println("가격 측정 완료");
		return null;
	}
	
	
	// 판매점 지역 선택시 판매점 이름 가져오기
	@ResponseBody
	@GetMapping("/shopOptionAdd")
	public Map<String, Object> shopOptionAdd(@RequestParam("shoploca") String shoploca) {
		
	    List<String> options = shopservice.addShopName(shoploca); // DB에서 옵션 가져오기
	    System.out.println("options : " + options);
		
	    // 응답 데이터 생성
	    Map<String, Object> response = new HashMap<>();
	    response.put("options", options);

	    return response;
	}
	
	// 신차 등록
	@PostMapping("/newsellCar")
	public String newsellCar(CarDTO dto,Model model,@RequestParam("img") MultipartFile uploadFile,HttpServletRequest request) {
		
		String[] fueltypes = request.getParameterValues("fuelType");
		//System.out.println("dto = " + dto);
		if(dto.getMaker().equals("기타")) {
			String maker = request.getParameter("maker1");
			dto.setMaker(maker);
		}
		dto.setCardate(new Date());
		// test 부분

		stopwatch.start();
		LocalDateTime startTime = stopwatch.getStartTime();
		System.out.println(startTime);

		// test 부분
		
		// 이미지 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setCarimage(fileName);
		// 이미지 저장
		if(!fileName.equals("")) {
			File file = new File(uploadpath,fileName);
			// 파일 저장
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Car sellcar = service.sellCar(dto);
		
		int cn = sellcar.getCarnum();
		
		System.out.println("fueltypes : " + fueltypes);
		// 연료 저장
	    if (fueltypes != null) {
	        for (String fueltype : fueltypes) {
	            FuelDTO fudto = new FuelDTO();
	            fudto.setCarnum(cn);
	            fudto.setFueltype(fueltype); // 단일 값으로 설정
		        System.out.println(fudto);
	            Fuel fuel = fuelService.fuelSave(fudto);
	            System.out.println("fuel : " + fuel);
	        }
	    }
		
		//////////////////////////////////////////
		// 판매점 저장하기
		// 필요한거 shopid (판매점코드), carnum
		// 선택한 판매점
		
		String shopname = request.getParameter("pointSelect");
		System.out.println("shopname : " + shopname);
		
		// 판매점 코드
		int shopid = shopservice.getShopId(shopname);
		
		Shop shop = new Shop();
	    shop.setShopid(shopid); // Shop 객체의 shopid 설정 (기타 필요한 필드도 설정)

	    Car car = new Car();
	    car.setCarnum(cn); // Car 객체의 carnum 설정 (기타 필요한 필드도 설정)

	    // ShopCarId 생성
	    ShopCarId shopCarId = new ShopCarId();
	    shopCarId.setShopid(shopid);
	    shopCarId.setCarnum(cn);

	    // ShopCar 객체 생성
	    Shopcar shopCar = new Shopcar();
	    shopCar.setId(shopCarId);
	    shopCar.setShop(shop); // Shop 객체 설정
	    shopCar.setCar(car); // Car 객체 설정
	    
	    
	    Shop shop2 = shopservice.ShopInfo(shopid);
	    Car car2 = service.getCarInfo(cn);
		// 연료 가저오기
		List<Fuel> fuel  = fuelService.fuelCarNum(cn);
		System.out.println("fuel : " + fuel);
	    
	    
		// db 저장
	    shopservice.saveShopCar(shopCar);
		model.addAttribute("shop", shop2);
		/////////////////////////////
		
		System.out.println("fueltypes : " + fueltypes);
		
		model.addAttribute("car", car2);
		model.addAttribute("fueltypes", fueltypes);
		model.addAttribute("fuel",fuel);
		// 판매자 정보
		String id = sellcar.getSellerid();
		Usermember user = memberService.searchUser1(id);
		
		model.addAttribute("user", user);
		model.addAttribute("req_page", "/car/sellCar");

		return "/member/adminView";
	}
	
	// 관리자에서 감정하기 - 중고차
	@GetMapping("/car/emotionCarPrice")
	public String emotionCarPrice(HttpServletRequest request,Model model) {
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		Car car = service.carView(carnum);
		
		//ad테이블로 판매점 정보 가저오기
		Admincontroller admin = ADCdao.getOne(seq);
		int shopid = admin.getShopid();
		Shop shop = shopservice.ShopInfo(shopid);
		
		
		// 차량테이블에 있는 판매 신청한 userid로 해당 유저 정보 가져오기
		Usermember user = memberService.searchUser1(car.getSellerid());
		String userName = user.getName();
		
		
		model.addAttribute("car", car);
		model.addAttribute("admin", admin);
		model.addAttribute("userName", userName);
		model.addAttribute("pg", pg);
		model.addAttribute("shop", shop);
		model.addAttribute("seq", seq);
		model.addAttribute("req_page", "/car/emotionCarPrice");
		
		return "/member/adminView";
	}
	
	// 중고차 감정 -> 감정승인 과정
	@PostMapping("/car/resultPrice")
	public String resultPrice(HttpServletRequest request,CarDTO dto,Model model) {
		double accident = 1.0;
		if(request.getParameter("accident")!=null && !request.getParameter("accident").equals("")) {
			accident = Double.parseDouble(request.getParameter("accident"));
		}
		int resultPrice = (int)(dto.getPrice() * accident);
		int pg = Integer.parseInt(request.getParameter("pg"));
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		// ad테이블 변경
		boolean adresult = ADCdao.update(seq);
		
		System.out.println("adresult : " + adresult);
		
		//System.out.println("pg : " + pg);
		stopwatch.stop();
		String stopTime = stopwatch.getStopTime_str();
		String totalTime = stopwatch.getElapsedTime();
		Totaltime total = totaltimeService.searchTime(carnum);
		TotaltimeDTO totaltimedto = new TotaltimeDTO();
		totaltimedto.setCarnum(carnum);
		totaltimedto.setStoptime(stopTime);
		totaltimedto.setTotaltime(totalTime);
		Totaltime resultTime = totaltimeService.updateEndtime(totaltimedto);
		System.out.println(carnum);
		System.out.println(resultTime);
		
		dto.setPrice(resultPrice);
		boolean result = service.updatePrice(dto);
		
		
		
		//System.out.println(result);
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		
		return "car/resultPrice";
	}
	
	
	// 신규차 구매 요청 리스트
		@GetMapping("/car/newCarBuyAdminList")
		public String newCarBuyAdminList(Model model, HttpServletRequest request) {
			int pg = 1;
			if(request.getParameter("pg") !=null && !request.getParameter("pg").equals("")) {
				pg = Integer.parseInt(request.getParameter("pg"));
			}
			
			// 해결여부 해결 = 1, 미해결 = 0
			int status = 0;
			if(request.getParameter("status") !=null && !request.getParameter("status").equals("")) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			
			System.out.println("status : " + status);
			
			// 목록 5개
			int endNum = pg * 5;
			int startNum = endNum - 4;
			
			//요청 타입
			String type= "구매";
			
			// adc 테이블에서 구매요청,미해결 목록 가져오기 ad테이블에서 상점이름, 차량이름,이미지 추가
			List<AdmindetailDTO> ADlist = ADCdao.test1(startNum,type,status);
			
			// adc 테이블에서 구매요청,미해결 목록 가져오기			
			int totalList = ADCdao.totalCount(type,status);
			System.out.println("크기비교 : " + ADlist.size() + "+" + totalList);
			
			// 가격이 측정되지 않은 차량의 갯수
			// car 테이블에서 가격이 0인 차량 가저오기
			int totalPage = (totalList + 4) / 5;
			
			int startPage = (pg-1)/3*3+1;
			int endPage = startPage + 2;
			if(endPage > totalPage) endPage = totalPage;
			
			
			// 데이터 공유
			model.addAttribute("pg",pg );
			model.addAttribute("ADlist",ADlist);
			model.addAttribute("status",status);
			model.addAttribute("totalPage",totalPage);
			model.addAttribute("startPage",startPage);
			model.addAttribute("endPage",endPage);
			model.addAttribute("req_page", "/car/newCarBuyAdminList");
			
			return "/member/adminView";
		}
		
		
	// 관리자가 구매요청을 자세히 보는거 
	@GetMapping("/car/newCheckCar")
	public String newCheckCar(Model model, HttpServletRequest request) {
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		System.out.println("seq :" + seq);
		Admincontroller admin= ADCdao.getOne(seq);
		
		System.out.println("ad테이블 seq :"+admin.getSeq());
		System.out.println("shopid : "+admin.getShopid());
		System.out.println("userid : "+admin.getUserid());
		System.out.println("carnum : "+admin.getCarnum());
		Car car = service.carView(admin.getCarnum());
		Shop shop = shopservice.ShopInfo(admin.getShopid());
		Usermember user = memberService.searchUser1(admin.getUserid());
		
		System.out.println(car);
		System.out.println(shop);
		System.out.println(user);
		
		model.addAttribute("seq",seq );
		model.addAttribute("admin",admin );
		model.addAttribute("car",car );
		model.addAttribute("shop",shop );
		model.addAttribute("user",user );
		model.addAttribute("req_page", "/car/newCheckCar");
		
		return "/member/adminView";
	}
	
	
	@PostMapping("/car/newCarOk")
	public String newcarOk(Model model, HttpServletRequest request) {
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int carnum = Integer.parseInt(request.getParameter("carnum"));
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		
		//승인하면 어캐 바껴야 할까영?
		System.out.println("seq : " + seq);
		System.out.println("carnum : " + carnum);
		System.out.println("shopid : " + shopid);
		
		
		// shopcar 테이블 변경
		// 이건 등록할때 올라가는데?
		
		// ad테이블 변경
		boolean result = ADCdao.update(seq);
		
		
		model.addAttribute("result",result);
		
		return "car/newCarOk";

	}
	
	// 관리자 시점에서 삭제하기
	@GetMapping("/car/realDelete")
	public String realDelete(Model model, HttpServletRequest request) {
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		//int carnum = Integer.parseInt(request.getParameter("carnum"));
		//int shopid = Integer.parseInt(request.getParameter("shopid"));
		
		//승인하면 어캐 바껴야 할까영?
		System.out.println("seq : " + seq);
		
		
		// shopcar 테이블 변경
		// 이건 등록할때 올라가는데?
		
		// ad테이블 변경
		boolean result = ADCdao.updateDelete(seq);
		
		
		model.addAttribute("result",result);
		
		return "car/realDelete";

	}
	
		
}





























