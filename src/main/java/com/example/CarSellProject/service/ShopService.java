package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CarSellProject.dao.ShopDAO;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.entity.Shopcar;
import com.example.CarSellProject.repository.CarRepository;
import com.example.CarSellProject.repository.ShopCarRepository;
import com.example.CarSellProject.repository.ShopRepository;

@Service
public class ShopService {
	
	@Autowired
	ShopDAO dao;
	
	// 차량 판매점 가져오기
	public List<Shop> getShop(int carnum) {
		
		
		return dao.getShop(carnum);
	}
	
	// 판매 지역 가져오기
	public List<Shop> getLocation(int carnum){
		
		return dao.getLocation(carnum);
		
	}
	
	// 최종 판매점 이름? 가져오기
	public List<String> getShopName(int carnum, String shoploca){
		return dao.getShopName(carnum, shoploca);
	}
	
	// 상점이름으로 상점 식별코드 가져오기
	public int getShopId(String shopname) {
		
		return dao.getShopId(shopname);
	}
	
	
	// 판매점 주소로 이름 가저오기 단일버전
	public List<String> addShopName(String shoploca){
		return dao.addShopName(shoploca);
	}
	
	// 판매점에 저장하기
	@Transactional
	public Shopcar saveShopCar(Shopcar shopcar) {
		
	    
	    return dao.saveShopCar(shopcar);
	}
	
	
	// 판매점 아이디로 판매점 가저오기
	public Shop ShopInfo(int shopid) {
		
		
		return dao.ShopInfo(shopid);
		
	}
	
}
