package com.example.CarSellProject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.CarSellProject.dto.ShopCarDTO;
import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.entity.Shopcar;
import com.example.CarSellProject.repository.CarRepository;
import com.example.CarSellProject.repository.ShopCarRepository;
import com.example.CarSellProject.repository.ShopRepository;


@Repository
public class ShopDAO {
	
	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	CarRepository carRepository;
	
	@Autowired
	ShopCarRepository shopCarRepository;
	
	// 차량 판매점 가져오기
	public List<Shop> getShop(int carnum) {
		
		List<Shop> shops = shopRepository.findShop(carnum);
		
		try {
		    List<Shop> shopss = shopRepository.findShop(carnum);
		    if (shopss.isEmpty()) {
		        // 비어있는 경우 처리
		        System.out.println("No shops found for carnum: " + carnum);
		    }
		} catch (Exception e) {
		    e.printStackTrace(); // 예외 정보 출력
		}
		
		return shops;
	}
	
	
	// 중고차 번호로 판매점 정보 가져오기
	public Shop getOldCarShop(int carnum) {
		return shopRepository.findShopOldCar(carnum);
	}
	
	// 판매 지역 가져오기
	public List<Shop> getLocation(int carnum){
		List<Shop> location = shopRepository.findLocation(carnum);
		
		return location;
		
	}
	
	// 최종 판매점 이름? 가져오기
	public List<String> getShopName(int carnum, String shoploca){
		return shopRepository.findShopfull(carnum,shoploca);
	}
	
	// 상점이름으로 상점 식별코드 가져오기
	public int getShopId(String shopname) {
		
		return shopRepository.getIdByShopname(shopname);
	}
	
	
	// 판매점 주소로 이름 가저오기 단일버전
	public List<String> addShopName(String shoploca){
		return shopRepository.findShopByname(shoploca);
	}
	
	// 판매점에 저장하기
	@Transactional
	public Shopcar saveShopCar(Shopcar shopcar) {
		
	    System.out.println(shopcar);
	    
	    return shopCarRepository.save(shopcar);// 저장 성공
	}
	
	
	// 판매점 아이디로 판매점 가저오기
	public Shop ShopInfo(int shopid) {
		
		Shop shop = shopRepository.findById(shopid).orElse(null);
		
		return shop;
		
	}
	

}
