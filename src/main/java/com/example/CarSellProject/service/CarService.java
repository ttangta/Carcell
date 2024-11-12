package com.example.CarSellProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.CarDAO;
import com.example.CarSellProject.dto.CarDTO;
import com.example.CarSellProject.entity.Car;

@Service
public class CarService {
		@Autowired
		CarDAO dao;
		
		public List<Car> carListOld(int startNum, int endNum, List<String> condition){
			return dao.oldcarList(startNum, endNum,condition);
		}
		public List<Car> carListNew(int startNum, int endNum, List<String> condition){
			return dao.newcarList(startNum, endNum,condition);
		}
		
		
		public int carTotal() {
			return (int) dao.carTotal();
		}
		
		
		public List<Car> hardsearch(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, int startNum, int endNum, List<String> condition
										,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {
			
			return dao.hardsearch(minPrice, maxPrice, makers, sizes, fuels,startNum,endNum,condition, makersNullCheck,sizesNullCheck,fuelsNullCheck);
			
		}
		
		public int newcarListCount(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, List<String> condition
									,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {
			
			return dao.newcarListCount(minPrice, maxPrice, makers, sizes, fuels, condition,makersNullCheck,sizesNullCheck,fuelsNullCheck);
		}
		
		public int oldcarListCount(int minPrice, int maxPrice, List<String> makers, List<String> sizes, List<String> fuels, List<String> condition
				,String makersNullCheck, String sizesNullCheck, String fuelsNullCheck) {

			return dao.oldcarListCount(minPrice, maxPrice, makers, sizes, fuels, condition,makersNullCheck,sizesNullCheck,fuelsNullCheck);
		}
		
		
		
		public List<Car> search(String search,int startNum, int endNum){
			return dao.search(search,startNum,endNum);
		}
		
		public int searchCount(String search) {
			return dao.searchCount(search);
		}
		
		// 내 차팔기
		public Car sellCar(CarDTO dto) {
			return dao.sellCar(dto);
		}
		
		// 관리자가 등록된 차량 확인 (가격이 0인 목록)
		public List<Car> registerCar(int startNum, int endNum){
			return dao.registerCar(startNum, endNum);
		}
		
		// (관리자 영역) 등록된 차량의 갯수 (가격이 0인 데이터 갯수)
		public int admintotal() {
			return dao.admintotal();
		}
		
		// 차량 상세보기
		public Car carView(int carnum) {
			return dao.carView(carnum);
		}
		// 차량 상세보기
		public Car getCarInfo(int carnum) {
			return dao.getCarInfo(carnum);
		}
		
		
		// 중고차 가격 측정
		public boolean updatePrice(CarDTO dto) {
			return dao.updatePrice(dto);
		}
		
		// 사용자 아이디로 차량 정보 찾기 (내 등록 차량 관리)
		public List<Car> myCar(String id) {
			return dao.myCar(id);
		}
		// 신규차량 5개 가져오기
		public List<Car> mainList(int startNum,int endNum){
			return dao.mainList(startNum, endNum);
		}
		
		// 가격 측정된 중고차 10개 가져오기
		public List<Car> oldCarList(){
			return dao.oldCarList();
		}

}
