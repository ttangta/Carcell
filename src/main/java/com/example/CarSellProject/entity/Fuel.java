package com.example.CarSellProject.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Fuel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "FUEL_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "FUEL_SEQUENCE_GENERATOR",
					sequenceName="seq_fuel",
					initialValue = 1, allocationSize = 1)
	private int seq;			//id값
	private int carnum;         	// 차령 고유 번호
    private String fueltype; 		// 연료 타입
}
