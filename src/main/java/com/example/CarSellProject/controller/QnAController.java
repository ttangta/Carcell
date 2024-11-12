package com.example.CarSellProject.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CarSellProject.dto.CommentsDTO;
import com.example.CarSellProject.dto.QnADTO;
import com.example.CarSellProject.entity.Comments;
import com.example.CarSellProject.entity.Qna;
import com.example.CarSellProject.entity.Usermember;
import com.example.CarSellProject.repository.CommentsRepository;
import com.example.CarSellProject.repository.UsermemberRepository;
import com.example.CarSellProject.service.CommentsService;
import com.example.CarSellProject.service.QnAService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class QnAController {
	@Autowired
	private QnAService service;
	@Autowired
	private CommentsService cmtservice;
	@Autowired
	private CommentsRepository cmtRepository;
	@Autowired
	private UsermemberRepository memberRepository;

	@GetMapping("/main/index")
	public String index() {
		return "main/index";
	}

	@GetMapping("/QnA/QnAList")
	public String QnAList(@RequestParam(value = "search", required = false) String search, Model model,
			HttpServletRequest request) {
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		int endNum = pg * 50;
		int startNum = endNum - 49;

		List<Qna> list;
		int totalA;

		if (search != null && !search.isEmpty()) {
			list = service.searchQnA(search); // 검색어를 기준으로 게시물 검색
			totalA = list.size(); // 검색 결과의 개수

			if (totalA == 0) {
				model.addAttribute("searchMessage", "검색 결과가 없습니다."); // 검색 결과가 없을 때 메시지 추가
			}
		} else {
			list = service.QnAList(startNum, endNum); // 기본 목록 조회
			totalA = service.getCount(); // 전체 게시물 개수
		}

		int totalP = (totalA + 49) / 50; // 총 페이지 수 계산

		int startPage = (pg - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		if (endPage > totalP)
			endPage = totalP;

		model.addAttribute("list", list);
		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		HttpSession session = request.getSession();
		model.addAttribute("Userid", session.getAttribute("Userid"));
		session.setAttribute("pg", pg);

		return "QnA/QnAList";
	}

	@GetMapping("/QnA/QnAWriteForm")
	public String QnAWriteForm() {
		return "QnA/QnAWriteForm";
	}

	@PostMapping("/QnA/QnAWrite")
	public String QnAWrite(Model model, HttpServletRequest request, QnADTO dto) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("Userid");
		String name = (String) session.getAttribute("UserName");

		dto.setId(id);
		dto.setName(name);
		dto.setLogtime(new Date());

		Qna qna = service.QnAWrite(dto);
		model.addAttribute("qna", qna);

		return "QnA/QnAWrite";
	}

	@GetMapping("/QnA/QnAView")
	public String QnAView(@RequestParam("seq") int seq, @RequestParam("pg") int pg, Model model, HttpSession session) {
		service.incrementHit(seq);
		Qna qna = service.QnAView(seq);
		List<Comments> comments = cmtservice.OrderByQnAId(seq);

		for (Comments comment : comments) {
			List<Comments> replies = cmtservice.getReplies(comment.getNum());
			comment.setReplies(replies);
		}

		model.addAttribute("comments", comments);
		model.addAttribute("QnA", qna);
		model.addAttribute("pg", pg);
		model.addAttribute("Userid", session.getAttribute("Userid"));

		return "QnA/QnAView";
	}

	@GetMapping("/QnA/QnADelete")
	public String QnADelete(Model model, HttpServletRequest request) {
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));

		boolean result = service.QnADelete(seq);
		model.addAttribute("pg", pg);
		model.addAttribute("result", result);

		return "QnA/QnADelete";
	}

	@GetMapping("/QnA/QnAModifyForm")
	public String boardModifyForm(@RequestParam("seq") int seq, @RequestParam("pg") int pg, Model model) {
		Qna qna = service.QnAView(seq);
		model.addAttribute("qna", qna);
		model.addAttribute("pg", pg);
		return "QnA/QnAModifyForm";
	}

	@PostMapping("/QnA/QnAModify")
	public String boardModify(Model model, HttpServletRequest request, QnADTO dto) {
		int pg = Integer.parseInt(request.getParameter("pg"));
		boolean result = service.QnAModify(dto);

		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", dto.getSeq());

		return "/QnA/QnAModify";
	}

	// 댓글 관련 처리

	public String formatCommentContent(String content) {
		return content.replace("\n", "<br>");
	}

	@PostMapping("/QnA/addComment")
	public String addComment(@RequestParam("qnaSeq") int qnaSeq, @RequestParam("comment") String commentContent,
			HttpServletRequest request) {
		Qna qna = service.QnAView(qnaSeq);
		Comments comment = new Comments();
		comment.setCmtcontent(commentContent);
		comment.setCmtlogtime(new Date());

		HttpSession session = request.getSession();
		String Userid = (String) session.getAttribute("Userid");

		if (Userid != null) {
			Usermember member = memberRepository.findById(Userid).orElse(null);
			if (member != null) {
				comment.setUser_id(member);
			} else {
				throw new IllegalStateException("Member not found.");
			}
		} else {
			throw new IllegalStateException("User must be logged in to post a comment.");
		}

		comment.setPost(qna);
		cmtservice.addComments(qnaSeq, comment);
		Integer pg = (Integer) session.getAttribute("pg");

		return "redirect:/QnA/QnAView?seq=" + qnaSeq + "&pg=" + pg;
	}

	@PostMapping("/QnA/addReply")
	public String addReply(@RequestParam("parentCommentId") Long parentCommentId,
			@RequestParam("replyContent") String replyContent, @RequestParam("qnaSeq") int qnaSeq,
			HttpServletRequest request) {
		Comments reply = new Comments();

		// 줄바꿈 문자를 \n 그대로 유지하도록 설정
		reply.setCmtcontent(replyContent); // formatCommentContent 제거 또는 줄바꿈 변환 처리 해제
		reply.setCmtlogtime(new Date());

		HttpSession session = request.getSession();
		String Userid = (String) session.getAttribute("Userid");

		if (Userid != null) {
			Usermember member = memberRepository.findById(Userid).orElse(null);
			if (member != null) {
				reply.setUser_id(member);
			} else {
				throw new IllegalStateException("Member not found.");
			}
		} else {
			throw new IllegalStateException("User must be logged in to post a reply.");
		}

		Qna qna = service.QnAView(qnaSeq);
		reply.setPost(qna);

		Comments parentComment = cmtRepository.findById(parentCommentId)
				.orElseThrow(() -> new RuntimeException("Parent comment not found"));
		reply.setParentComment(parentComment);

		cmtservice.addReply(parentCommentId, reply);
		Integer pg = (Integer) session.getAttribute("pg");

		return "redirect:/QnA/QnAView?seq=" + qnaSeq + "&pg=" + pg + "#comment-content-" + parentCommentId;
	}

	@PostMapping("/QnA/ReplyUpdate")
	public String updateReply(@RequestParam("replyNum") Long replyNum,
			@RequestParam("replyContent") String replyContent, HttpServletRequest request) {
		Comments reply = cmtRepository.findById(replyNum).orElseThrow(() -> new RuntimeException("Reply not found"));
		reply.setCmtcontent(formatCommentContent(replyContent));

		HttpSession session = request.getSession();
		String Userid = (String) session.getAttribute("Userid");

		if (Userid != null) {
			Usermember member = memberRepository.findById(Userid).orElse(null);
			if (member != null && member.equals(reply.getUser_id())) {
				cmtRepository.save(reply);
			} else {
				throw new IllegalStateException("You are not allowed to update this reply.");
			}
		} else {
			throw new IllegalStateException("User must be logged in to update a reply.");
		}

		Integer pg = (Integer) session.getAttribute("pg");
		Qna qna = reply.getPost();
		return "redirect:/QnA/QnAView?seq=" + qna.getSeq() + "&pg=" + pg;
	}

	@PostMapping("/QnA/CommentDelete")
	public String CommentDelete(@RequestParam("num") Long num, @RequestParam("seq") int seq,
			@RequestParam("pg") int pg) {
		try {
			cmtservice.CommentDelete(num);
		} catch (Exception e) {
			// 에러 처리
		}
		return "redirect:/QnA/QnAView?seq=" + seq + "&pg=" + pg;
	}

	@PostMapping("/QnA/CommentUpdate")
	@ResponseBody
	public ResponseEntity<CommentsDTO.Response> updateComment(@RequestBody CommentsDTO.Request request) {
		CommentsDTO.Response response = new CommentsDTO.Response();
		try {
			cmtservice.updateComment(request.getNum(), request.getCmtcontent());
			response.setSuccess(true);
			response.setMessage("댓글이 성공적으로 수정되었습니다.");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/QnA/test")
	public String test() {
		return "QnA/test";
	}
}
