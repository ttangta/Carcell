package com.example.CarSellProject.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CarSellProject.dto.EventDTO;
import com.example.CarSellProject.entity.Event;
import com.example.CarSellProject.service.EventService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {
	@Autowired
	EventService service;
	@Value("${project.upload.path}")
	private String uploadpath;

	@GetMapping("/index2")
	public String index2() {
		return "board/index2";
	}

	@GetMapping("/eventList")
	public String eventList(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");

	    System.out.println("Logged in userId: " + userId); // 로그인된 사용자 아이디 출력
		// 1. 데이터 처리
		int pg = 1;
		if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 목록 : 5개
		int endNum = pg * 5;
		int startNum = endNum - 4;

		List<Event> list = service.eList(startNum, endNum);

		// 페이징 : 3블럭
		int totalA = service.getTotalA(); // 총 글 수 가져옴
		int totalP = (totalA + 4) / 5;

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;

		// 2. 데이터 공유
		model.addAttribute("list", list);
		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("userId", userId);

		System.out.println(service.eList(1, 5));

		return "board/eventList";
	}

	// 상세보기
	@GetMapping("/eventView")
	public String eventView(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// db
		service.updateHit(seq);
		Event event = service.eventView(seq);

		// 2. 데이터 공유
		model.addAttribute("event", event);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		model.addAttribute("userId", userId);

		// 3. view 파일 선택
		return "board/eventView";
	}

	// 글 작성
	@GetMapping("/eventWriteForm")
	public String eventdWriteForm(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("Userid");
		model.addAttribute("userId", userId);

		return "board/eventWriteForm";
	}

	// 글 작성 처리
	@PostMapping("/eventWrite")
	public String eventWrite(EventDTO dto, Model model, @RequestParam("img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		System.out.println("dto = " + dto);
		// file명 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setEventimg(fileName);
		dto.setLogtime(new Date());
		System.out.println("dto = " + dto);

		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			System.out.println("file = " + file);
			// 파일저장

			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// db
		boolean result = service.eventWrite(dto);

		// 2. 데이터 공유
		model.addAttribute("result", result);

		// 3. view 파일 선택
		return "board/eventWrite";
	}

	// 삭제
	@GetMapping("/eventDelete")
	public String eventDelete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// db
		boolean result = service.eventDelete(seq);

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("result", result);

		// 3. view 처리 파일 선택
		return "board/eventDelete";
	}

	// 수정
	@GetMapping("/eventModifyForm")
	public String eventModifyForm(EventDTO dto, Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("Userid");
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));

		// db : 상세보기 데이터
		Event event = service.eventView(seq);

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("event", event);
		model.addAttribute("userId", userId);

		// 3. view 처리 파일 선택
		return "board/eventModifyForm";
	}

	// 수정 처리
	@PostMapping("/eventModify")
	public String eventModify(EventDTO dto, Model model, HttpServletRequest request, @RequestParam("img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		// file명 처리
		String fileName = uploadFile.getOriginalFilename();
		dto.setEventimg(fileName);
		System.out.println("dto = " + dto);

		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			System.out.println("file = " + file);
			// 파일저장

			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// db
		boolean result = service.eventModify(dto);

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", dto.getSeq());
		model.addAttribute("result", result);

		// 3. view 처리 파일 선택
		return "board/eventModify";
	}
}
