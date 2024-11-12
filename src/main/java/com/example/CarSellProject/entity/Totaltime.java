package com.example.CarSellProject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Totaltime {
	private String id;
	@Id
	private int carnum;
	private String starttime;
	private String stoptime;
	private String totaltime;
}
