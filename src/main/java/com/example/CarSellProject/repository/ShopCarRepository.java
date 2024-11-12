package com.example.CarSellProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CarSellProject.entity.ShopCarId;
import com.example.CarSellProject.entity.Shopcar;

public interface ShopCarRepository extends JpaRepository<Shopcar, ShopCarId>{
	
	
	
}
