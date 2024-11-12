package com.example.CarSellProject.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
	
	
	@Id
	private int shopid;
	private String location;
    private String address;
    private String shopname;
    private String shopmail;
	
	
}
