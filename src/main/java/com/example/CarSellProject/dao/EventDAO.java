package com.example.CarSellProject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.EventDTO;
import com.example.CarSellProject.entity.Event;
import com.example.CarSellProject.repository.EventRepository;


@Repository
public class EventDAO {
	@Autowired
	EventRepository repository;
	
	// 저장
	public boolean eventWrite(EventDTO dto) {		
		Event event = dto.toEntity();	
		return repository.save(event) != null;
	}

	// 목록
	public List<Event> eList(int startNum, int endNum) {
		return repository.findByStartnumAndEndnum(startNum, endNum);
	}

	// 전체 데이터 갯수
	public int getTotalA() {
		return (int)repository.count();
	}

	// 상세보기
	public Event eventView(int seq) {
		return repository.findById(seq).orElse(null);
	}
	
	// 조회수 증가
	public Event updateHit(int seq) {
		// 1. 기존 데이터 가져오기
		Event event = repository.findById(seq).orElse(null);
		
		if(event != null) {
			// 2. 수정
			event.setEventhit(event.getEventhit() + 1);
			// 3. 저장
			return repository.save(event);
		}
		return null;
	}

	// 삭제
	public boolean eventDelete(int seq) {
		// 기존 데이터 확인
		Event event = repository.findById(seq).orElse(null);
		if(event != null) {
			// 삭제하기
			repository.deleteById(seq);			
		}
		return !repository.existsById(seq);
	}
	// 수정하기
	public boolean eventModify(EventDTO dto) {
		// 1. 기존 데이터 가져오기
		Event event = repository.findById(dto.getSeq()).orElse(null);
		boolean result = false;
		if(event != null) {
			// 2. 수정
			event.setEventsubject(dto.getEventsubject());
			event.setEventsubject2(dto.getEventsubject2());
			event.setEventlong(dto.getEventlong());
			event.setEventcontent(dto.getEventcontent());
			event.setEventimg(dto.getEventimg());
			
			// 3. 저장
			Event event_result = repository.save(event);
			if(event_result != null) result = true;
		}
		return result;
	}
}






