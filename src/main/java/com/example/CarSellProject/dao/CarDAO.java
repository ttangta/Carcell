package com.example.CarSellProject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.CarDTO;
import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.repository.CarRepository;
import com.example.CarSellProject.repository.ShopRepository;


@Repository
public class CarDAO {
	
	@Autowired
	CarRepository carRepository;
	
	public List<Car> newcarList(int startNum, int endNum, List<String> condition){
		return carRepository.findByStartnumAndEndnumNew(startNum, endNum,condition);
	}
	public List<Car> oldcarList(int startNum, int endNum, List<String> condition){
		return carRepository.findByStartnumAndEndnumOld(startNum, endNum,condition);
	}
	
	
	public int carTotal() {
		return (int) carRepository.count();
	}
	
	
	public List<Car> hardsearch(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, int startNum, int endNum, List<String> condition
									,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {
		
		return carRepository.hardsearch(minPrice, maxPrice, makers, sizes, fuels,startNum,endNum,condition, makersNullCheck,sizesNullCheck,fuelsNullCheck);
		
	}
	
	public int newcarListCount(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, List<String> condition
								,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {
		return carRepository.newcarListCount(minPrice, maxPrice, makers, sizes, fuels, condition,makersNullCheck,sizesNullCheck,fuelsNullCheck);
	}
	
	public int oldcarListCount(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, List<String> condition
			,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {
		return carRepository.oldcarListCount(minPrice, maxPrice, makers, sizes, fuels, condition,makersNullCheck,sizesNullCheck,fuelsNullCheck);
	}
	
	
	// 세부정보 가져오기
	public Car getCarInfo(int carnum) {
		
		return carRepository.findById(carnum).orElse(null);
	}
	
	
	/////////김정훈
	public List<Car> search(String search,int startNum, int endNum){
		return carRepository.search(search,startNum,endNum);
	}
	
	public int searchCount(String search) {
		return carRepository.searchCount(search);
	}
	
	// 내 차 팔기 등록
	public Car sellCar(CarDTO dto) {
		Car sellcar = dto.toEntity();
		System.out.println("sellcar : " + sellcar);
		System.out.println("dto.toentity : " + dto.toString());
		return carRepository.save(sellcar);
	}
	
	// (관리자 영역)등록된 차량 확인 (가격이 0인 목록)
	public List<Car> registerCar(int startNum, int endNum){
		return carRepository.registerCar(startNum, endNum);
	}
	
	// (관리자 영역) 등록된 차량의 갯수 (가격이 0인 데이터 갯수)
	public int admintotal() {
		return carRepository.admintotal();
	}
	
	// 차량 상세보기
	public Car carView(int carnum) {
		return carRepository.findById(carnum).orElse(null);
	}
	
	// 중고차 가격 측정
	public boolean updatePrice(CarDTO dto) {
		boolean result = false;
		Car car = carRepository.findById(dto.getCarnum()).orElse(null);
		
		if(car != null) {
			// 금액수정
			car.setPrice(dto.getPrice());
			Car update_car = carRepository.save(car);
			if(update_car != null) result = true;
			return result;
		}
		return result;
	}
	
	// 사용자 아이디로 차량 정보 찾기 (내 등록 차량 관리)
	public List<Car> myCar(String id) {
		return carRepository.myCar(id);
	}
	
	
	// 중고차 구매시 차량상태 예약중으로 변경
	public boolean updateCondition(int carnum) {
		boolean result = false;
		Car car = carRepository.findById(carnum).orElse(null);
		
		if(car != null) {
			// 금액수정
			car.setCondition("sellrdy");
			Car update_car = carRepository.save(car);
			if(update_car != null) result = true;
			return result;
		}
		return result;
	}
	
	// 신규차량 5개 가져오기
	public List<Car> mainList(int startNum,int endNum){
		return carRepository.mainList(startNum, endNum);
	}
	
	// 가격 측정된 중고차 10개 가져오기
	public List<Car> oldCarList(){
		return carRepository.oldCarList();
	}
	
}
