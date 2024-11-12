package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.QnADAO;
import com.example.CarSellProject.entity.Comments;

@Service
public class CommentsService {
	@Autowired
	private QnADAO dao;

	// 댓글 추가
	public Comments addComments(int qna_seq, Comments comment) {
		return dao.addComments(qna_seq, comment);
	}

	// 대댓글 추가
	public Comments addReply(Long parentCommentId, Comments reply) {
		return dao.addReply(parentCommentId, reply);
	}

	// 댓글 날짜순으로 조회(최신순 부터)
	public List<Comments> OrderByQnAId(int qna_seq) {
		return dao.OrderByQnAId(qna_seq);
	}

	// 특정 댓글의 대댓글 조회
	// 대댓글 목록을 계층적으로 조회
	public List<Comments> getReplies(Long parentCommentId) {
		return dao.getReplies(parentCommentId);
	}

	// 댓글 삭제
	public void CommentDelete(Long num) {
		dao.CommentDelete(num);
	}

	// 댓글 수정
	public void updateComment(Long num, String content) {
		dao.updateComment(num, content);
	}
}
