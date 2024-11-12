package com.example.CarSellProject.dto;



import com.example.CarSellProject.entity.Fuel;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FuelDTO {
	
	private int seq;			//id값
    private int carnum;         	// 차령 고유 번호
    private String fueltype; 		// 연료 타입
    
    
    public Fuel toEntity() {
    	
    	return new Fuel(seq, carnum, fueltype);
    }
    
}
