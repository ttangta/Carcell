package com.example.CarSellProject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.TotaltimeDTO;
import com.example.CarSellProject.entity.Totaltime;
import com.example.CarSellProject.repository.TotaltimeRepository;
import com.example.CarSellProject.repository.UsermemberRepository;

@Repository
public class TotalTimeDAO {
	@Autowired
	TotaltimeRepository totaltimeRepository;
	
	// 시작시간 저장
	public Totaltime inputStartTime(TotaltimeDTO dto) {
		Totaltime totaltime = dto.toEntity();
		return totaltimeRepository.save(totaltime);
	}
	
	// 차량 번호에 따른 totaltime 가져오기
	public Totaltime searchTime(int carnum) {
		return totaltimeRepository.time(carnum);
	}
	
	// 종료시간 & 총 소유시간 업데이트
	public Totaltime updateEndtime(TotaltimeDTO dto) {
		Totaltime total = searchTime(dto.getCarnum());
		if(total != null) {
			total.setStoptime(dto.getStoptime());
			total.setTotaltime(dto.getTotaltime());
			Totaltime result = totaltimeRepository.save(total);
			return result;
		}else {
			return null;
		}
	}
	
	// 시간 확인 
	public List<Totaltime> times(String id){
		return totaltimeRepository.times(id);
	}
	
}
