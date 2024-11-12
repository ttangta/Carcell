package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.FuelDAO;
import com.example.CarSellProject.dto.FuelDTO;
import com.example.CarSellProject.entity.Fuel;

@Service
public class FuelService {
	@Autowired
	FuelDAO dao;
	
	// 유종 정보 저장
	public Fuel fuelSave(FuelDTO dto) {
		return dao.fuelSave(dto);
	}
	
	// 차번호로 해당 연료값
	public List<Fuel> fuelCarNum(int carnum) {
		return dao.fuelCarNum(carnum);
		
	}
}