package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.EventDAO;
import com.example.CarSellProject.dto.EventDTO;
import com.example.CarSellProject.entity.Event;


@Service
public class EventService {
	@Autowired
	EventDAO dao;
	
	// 저장
	public boolean eventWrite(EventDTO dto) {		
		return dao.eventWrite(dto);
	}

	// 목록
	public List<Event> eList(int startNum, int endNum) {
		return dao.eList(startNum, endNum);
	}

	// 전체 데이터 갯수
	public int getTotalA() {
		return dao.getTotalA();
	}

	// 상세보기
	public Event eventView(int seq) {
		return dao.eventView(seq);
	}
	
	// 조회수 증가
	public Event updateHit(int seq) {
		return dao.updateHit(seq);
	}

	// 삭제
	public boolean eventDelete(int seq) {
		return dao.eventDelete(seq);
	}
	
	// 수정하기
	public boolean eventModify(EventDTO dto) {
		return dao.eventModify(dto);
	}
}
