package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.QnADAO;
import com.example.CarSellProject.dto.QnADTO;
import com.example.CarSellProject.entity.Qna;


@Service
public class QnAService {

	@Autowired
	private QnADAO dao;

	// 게시물 목록
	public List<Qna> QnAList(int startNum, int endNum) {
		return dao.QnAList(startNum, endNum);
	}

	// 게시물 개수
	public int getCount() {
		return dao.getCount();
	}
	
	// 게시물 검색
	public List<Qna> searchQnA(String search) {
	    return dao.searchQnA(search);
	}


	// 게시물 작성
	public Qna QnAWrite(QnADTO dto) {
		return dao.QnAWrite(dto);
	}

	// 상세보기
	public Qna QnAView(int seq) {
		return dao.QnAView(seq);
	}

	// 조회수 증가
	public void incrementHit(int seq) {
		dao.incrementHit(seq);
	}

	// 게시물 삭제
	public boolean QnADelete(int seq) {
		return dao.QnADelete(seq);
	}

	// 게시물 수정
	public boolean QnAModify(QnADTO dto) {
		return dao.QnAModify(dto);
	}
}
