package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.TotalTimeDAO;
import com.example.CarSellProject.dto.TotaltimeDTO;
import com.example.CarSellProject.entity.Totaltime;

@Service
public class TotalTimeService {
	
	@Autowired
	TotalTimeDAO dao;
	
	public Totaltime inputStartTime(TotaltimeDTO dto) {
		return dao.inputStartTime(dto);
	}
	
	// 차량 번호에 따른 totaltime 가져오기
	public Totaltime searchTime(int carnum) {
		return dao.searchTime(carnum);
	}
	public Totaltime updateEndtime(TotaltimeDTO dto) {
		return dao.updateEndtime(dto);
	}
	
	// 시간 확인 
	public List<Totaltime> times(String id){
		return dao.times(id);
	}
}
