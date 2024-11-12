package com.example.CarSellProject.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CarSellProject.dao.AdminControllerDAO;
import com.example.CarSellProject.dto.AdmindetailDTO;
import com.example.CarSellProject.dto.UserMemberDTO;
import com.example.CarSellProject.entity.Admincontroller;
import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.entity.Totaltime;
import com.example.CarSellProject.entity.Usermember;
import com.example.CarSellProject.service.CarService;
import com.example.CarSellProject.service.FuelService;
import com.example.CarSellProject.service.ShopService;
import com.example.CarSellProject.service.TotalTimeService;
import com.example.CarSellProject.service.UsermemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	UsermemberService service;
	
	@Autowired
	CarService carService;
	
	@Autowired
	TotalTimeService totalService;
	
	@Autowired
	AdminControllerDAO ACdao;
	
	
	
	@Autowired
	ShopService shopservice;
	
	@Autowired
	FuelService fuelService;
	
	
	// 메인화면
	@GetMapping("/main")
	public String main1(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Car> list = carService.mainList(1, 10);
		List<Car> oldCarlist = carService.oldCarList();
		model.addAttribute("oldCarlist", oldCarlist);
		model.addAttribute("list", list);
		String userId = (String) session.getAttribute("Userid");
		model.addAttribute("userId", userId);
		return "/main/main";
	}
	
	// 검색
		@GetMapping("/search_car1")
		public String search_car(HttpServletRequest request,Model model) {
			int pg = 1;
			
			// 목록은 한 화면에 5개씩
			int endNum = pg * 5;
			int startNum = endNum - 4;
			
			String search = null;
			if(request.getParameter("search_name") != null || !request.getParameter("search_name").equals("")) {
				search = "%" +request.getParameter("search_name") + "%";
			}
			List<Car> list = carService.search(search,startNum,endNum);
			
			// 검색에 대한 총 데이터 수
			int searchCount = carService.searchCount(search);
			int searchCountP = (searchCount + 4) / 5;
			int startPage = (pg -1)/3 * 3 + 1;
			int endPage = startPage + 2;
			if(endPage > searchCountP) endPage = searchCountP;
			System.out.println(list);
			System.out.println(searchCount);
			
			model.addAttribute("list", list);
			model.addAttribute("pg", pg);
			model.addAttribute("searchCountP", searchCountP);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage",endPage);
			
			return "car/search_carList";
		}
	
	
	
	
	// 로그인 창
	@GetMapping("/member/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	// 로그인
	@PostMapping("/member/login")
	public String login(UserMemberDTO dto,Model model, HttpServletRequest request) {
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		Usermember user = service.login(dto);
		
		System.out.println(user);
		if(user != null) {
			//System.out.println("로그인 성공");
			model.addAttribute("user", user);
			HttpSession session = request.getSession();
			session.setAttribute("Userid", user.getId());
			session.setAttribute("UserName", user.getName());
			session.setAttribute("UserType", user.getType());
			return "/member/loginOk";
		}else {
			return "/member/loginFalse";
		}
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("Userid");
		session.removeAttribute("UserName");
		session.removeAttribute("UserType");
		return "/member/logout";
	}
	
	// 회원가입 창
	@GetMapping("/member/joinForm")
	public String joinForm() {
		return "/member/joinForm";
	}
	
	// 회원가입
		@PostMapping("/member/join")
		public String join(UserMemberDTO dto, Model model, HttpServletRequest request) {
			if(dto.getType() == 1) {
				dto.setEmail1("springBootthird");
				dto.setEmail2("gmail.com");
			}
			String email2 = dto.getEmail2();
			if(email2 == null) {
				dto.setEmail2(request.getParameter("writeEmail"));
			}
			boolean result = service.join(dto);
			model.addAttribute("result", result);
			return "/member/join";
		}

	
	// 아이디 중복 검사
	@GetMapping("/member/idCheck")
	public String idCheck(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		
		boolean result = service.idCheck(id);
		
		model.addAttribute("id", id);
		model.addAttribute("result", result);
		return "/member/idCheck";
	}
	
	// 아이디 찾기 / 비밀번호찾기 폼
	@GetMapping("/member/findMemberForm")
	public String findMemberForm(HttpServletRequest request,Model model) {
		String menu = request.getParameter("menu");
		int error = 0;
		
		if(request.getParameter("error") != null && !request.getParameter("error").equals("")) {
			error = Integer.parseInt(request.getParameter("error"));
		}
		model.addAttribute("error", error);
		model.addAttribute("menu", menu);
		return "/member/findMemberForm";
	}
	
	// 아이디 찾기
	@PostMapping("/member/findId")
	public String findId(UserMemberDTO dto, Model model) {
		boolean result = service.findId(dto);
		String id = "";
		if(result) {
			id = service.viewId(dto);
			model.addAttribute("id", id);
		}
		System.out.println("결과 = " + result);
		model.addAttribute("result", result);
		return "/member/findIdresult";
	}
	
	// 비밀번호 재설정 전 아이디 존재 여부 검사
	@PostMapping("/member/findPw")
	public String findPw(UserMemberDTO dto, Model model) {
		Usermember user = service.searchUser(dto);
		String id = "";
		boolean result = false;
		if(user != null) {
			result = true;
			id = user.getId();
			model.addAttribute("id", id);
		}
		model.addAttribute("result", result);
		return "/member/findPwresult";
	}
	
	// 비밀번호 재설정 폼
	@GetMapping("/member/pwmodifyForm")
	public String pwmodifyForm(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "/member/pwmodifyForm";
	}
	
	// 비밀번호 재설정
	@PostMapping("/member/pwmodify")
	public String pwmodify(HttpServletRequest request, Model model,UserMemberDTO dto) {
		String id = request.getParameter("id");
		Usermember user = service.searchUser1(id);
		user.setPw(dto.getPw());
		boolean result = service.pwUpdate(user);
		model.addAttribute("result", result);
		return "/member/pwmodify";
	}
	
	// 회원정보 수정 전 비밀번호 입력 창
	@GetMapping("/member/beforeModifyUser")
	public String beforModifyUser(Model model,HttpServletRequest request) {
		model.addAttribute("req", "/member/beforeModifyUser");
		return "/main";
	}
	
	// 다원님꺼
	// 회원정보 수정 폼
	@GetMapping("/member/modifyForm")
	public String modifyForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    
		Usermember user = service.userView(userId);
		
		model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		
		return "/member/modifyForm";
	}
	
	// 회원정보 수정
	@PostMapping("/member/modify")
	public String modify(UserMemberDTO dto,Model model, HttpServletRequest request) {
		System.out.println("modify");
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    
		boolean result = service.userUpdate(dto);
		System.out.println("dto = " + dto);
		
		model.addAttribute("result", result);
		model.addAttribute("userId", userId);
		
		return "/member/modify";
	}
	
	// 마이페이지
	@GetMapping("/member/userView")
	public String selfPage(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    
	    if (userId == null) {
	        return "/member/goLogin";
	    }

	    Usermember user = service.userView(userId);
	    
	    
	    
	    //////////구매//////////
	    // 사용자이름 + type가 구매인 리스트
	    String type1= "구매";
	    //List<Admincontroller> ADList = ACdao.getListByUser(userId, type);
		List<AdmindetailDTO> ADBList = ACdao.SellListByuserid(userId,type1);
	    
	    
	    List<Car> myCar = carService.myCar(userId);
	    List<Totaltime> times = totalService.times(userId);
	    System.out.println(times);
	    //System.out.println(myCar);
	    System.out.println(myCar.size());
	    List<Integer> index = new ArrayList<>();
	    for(int i = 0 ; i<myCar.size(); i++) {
	    	index.add(i);
	    }
	    
	    
	    model.addAttribute("myCar", myCar);
	    model.addAttribute("times", times);
	    model.addAttribute("index", index);
	    
	    
	    
	    model.addAttribute("ADBList", ADBList);
	    
	    //////////판매//////////
	    
	    String type2= "판매";
	    List<AdmindetailDTO> ADSList = ACdao.SellListByuserid(userId,type2);
	    model.addAttribute("ADSList", ADSList);
	    
	    
	    
	    model.addAttribute("user", user);
	    model.addAttribute("userId", userId);
		
		
		
		return "/member/userView";
	}
	// 회원정보 보기
	@GetMapping("/member/userView1")
	public String userView(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    
	    if (userId == null) {
	        return "/member/goLogin";
	    }

	    Usermember user = service.userView(userId);
	    
	    model.addAttribute("user", user);
	    model.addAttribute("userId", userId);

	    return "/member/userView1";
	}
	// 내 등록 차량 보기( 판매쪽이 나와야함)
	@GetMapping("/member/userUploadCar1")
	public String userUploadCar(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    String type="판매";
	    
	    if (userId == null) {
	        return "/member/goLogin";
	    }
	    
	    // 사용자이름 + type가 판매인 리스트
	    //List<Admincontroller> ADList = ACdao.getListByUser(userId, type);
		List<AdmindetailDTO> ADSList = ACdao.SellListByuserid(userId,type);
	    
	    Usermember user = service.userView(userId);
	    
	    List<Car> myCar = carService.myCar(userId);
	    List<Totaltime> times = totalService.times(userId);
	    System.out.println(times);
	    //System.out.println(myCar);
	    System.out.println(myCar.size());
	    List<Integer> index = new ArrayList<>();
	    for(int i = 0 ; i<myCar.size(); i++) {
	    	index.add(i);
	    }
	    System.out.println(index);
	    System.out.println("ADList " + ADSList);
	    
	    
	    model.addAttribute("myCar", myCar);
	    model.addAttribute("times", times);
	    model.addAttribute("index", index);
	    
	    
	    
	    model.addAttribute("ADSList", ADSList);
	    
	    model.addAttribute("user", user);
	    model.addAttribute("userId", userId);

	    return "/member/userUploadCar1";
	}
	// 내 구매 요청 현황 보기 (구매쪽이 나와야함)
	@GetMapping("/member/userReservation1")
	public String userReservation(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    String type="구매";
	    
	    if (userId == null) {
	        return "/member/goLogin";
	    }
	    
	    // 사용자이름 + type가 판매인 리스트
	    //List<Admincontroller> ADList = ACdao.getListByUser(userId, type);
		List<AdmindetailDTO> ADBList = ACdao.SellListByuserid(userId,type);
	    
	    Usermember user = service.userView(userId);
	    
	    List<Car> myCar = carService.myCar(userId);
	    List<Totaltime> times = totalService.times(userId);
	    System.out.println(times);
	    //System.out.println(myCar);
	    System.out.println(myCar.size());
	    List<Integer> index = new ArrayList<>();
	    for(int i = 0 ; i<myCar.size(); i++) {
	    	index.add(i);
	    }
	    System.out.println(index);
	    System.out.println("ADList " + ADBList);
	    
	    
	    model.addAttribute("myCar", myCar);
	    model.addAttribute("times", times);
	    model.addAttribute("index", index);
	    
	    
	    
	    model.addAttribute("ADList", ADBList);
	    
	    model.addAttribute("user", user);
	    model.addAttribute("userId", userId);

	    return "/member/userReservation1";
	}
	
	
	
	
	
	
	
	//로그인으로 보내기
    @GetMapping("/goLogin")
    public String goLogin(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
	    model.addAttribute("userId", userId);
	    
        return "/member/goLogin";
    }
    
	// 관리자 페이지
	@GetMapping("/member/adminView")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	public String adminjoinForm() {
		return "/member/adminView";
	}
    
	
	// 관리자가 구매요청을 자세히 보는거 
	@GetMapping("/car/userWantInfo")
	public String newCheckCar(Model model, HttpServletRequest request) {
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		System.out.println("seq :" + seq);
		Admincontroller admin= ACdao.getOne(seq);
		
		System.out.println("ad테이블 seq :"+admin.getSeq());
		System.out.println("shopid : "+admin.getShopid());
		System.out.println("userid : "+admin.getUserid());
		System.out.println("carnum : "+admin.getCarnum());
		Car car = carService.carView(admin.getCarnum());
		Shop shop = shopservice.ShopInfo(admin.getShopid());
		Usermember user = service.searchUser1(admin.getUserid());
		
		System.out.println(car);
		System.out.println(shop);
		System.out.println(user);
		
		model.addAttribute("seq",seq );
		model.addAttribute("admin",admin );
		model.addAttribute("car",car );
		model.addAttribute("shop",shop );
		model.addAttribute("user",user );
		
		return "/car/userWantInfo";
	}
	
	// 탈퇴해보자
	@GetMapping("/deleteAccount")
	public String deleteAccount(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("Userid");
		
	    Usermember user = service.userView(userId);
	    model.addAttribute("user", user);
		
		
		model.addAttribute("userId", userId);
		return "/member/deleteAccount";
	}
	
	
	@PostMapping("/deleteAccountAfter")
	public String deleteAccountAfter( Model model, HttpServletRequest request) {
		// 계정 삭제 로직 수행
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("Userid");
		System.out.println("userid : " + userId);
		
		boolean result =  service.deleteAcc(userId);
		
		System.out.println("result : " + result);
		if(result) {
			session.removeAttribute("Userid");
		}
		model.addAttribute("result", result);
		
	    return "/member/deleteAccountAfter"; // 결과 페이지로 이동
	}
	
	
	
	
}
