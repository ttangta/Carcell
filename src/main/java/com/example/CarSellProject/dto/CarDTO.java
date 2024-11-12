package com.example.CarSellProject.dto;

import java.util.Date;

import com.example.CarSellProject.entity.Car;

//import com.example.car.entity.Car;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarDTO {
	
    private int carnum;						// 차량 고유 번호 식별용도 
    private String maker;     	            // 제조사
    private String modelname;              // 모델명
    private String carsize;     		    // 차종( 소형 대형 어쩌구)
    private int caryear;                    //  -- 연식 
    private int mileage;                    // -- 주행거리 (숫자로 변경)
    private int price;                      // -- 가격
    private String condition;               //-- 상태(신, 중고)
    private String carimage;                //-- 사진 URL (길이 늘림)
    private Date cardate;                   //-- 등록일 (DATE 타입으로 변경)
    private int carhit;                     // -- 조회수
    private String sellerid;               //  -- 판매자 아이디
    private String buyerid;                //-- 구매자 아이디
    
    public Car toEntity() {
    	return new Car(carnum, maker, modelname, carsize, caryear, mileage, price, condition, carimage, cardate, carhit, sellerid, buyerid);
    }
}
