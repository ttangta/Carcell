package com.example.CarSellProject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.FuelDTO;
import com.example.CarSellProject.entity.Fuel;
import com.example.CarSellProject.repository.FuelRepository;

@Repository
public class FuelDAO {
	@Autowired
	FuelRepository fuelRepository;
	
	// 저장
	public Fuel fuelSave(FuelDTO dto) {
		Fuel fuel = dto.toEntity();
		return fuelRepository.save(fuel);
	}
	
	// 차번호로 해당 연료값
	public List<Fuel> fuelCarNum(int carnum) {
		List<Fuel> list =  fuelRepository.FuelByCarnum(carnum);
		return list;
		
	}
}
