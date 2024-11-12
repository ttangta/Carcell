package com.example.CarSellProject.dto;

import java.util.Date;

import com.example.CarSellProject.entity.Totaltime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TotaltimeDTO {
	private String id;
	private int carnum;
	private String starttime;
	private String stoptime;
	private String totaltime;
	
	public Totaltime toEntity() {
		return new Totaltime(id, carnum, starttime, stoptime, totaltime);
	}
}
