package com.example.CarSellProject.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "CAR_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "CAR_SEQUENCE_GENERATOR",
						sequenceName="seq_car",
						initialValue = 1, allocationSize = 1)
	@Column(name = "carnum")
    private int carnum;						// 차량 고유 번호 식별용도 
    private String maker;     	            // 제조사
    private String modelname;              // 모델명
    private String carsize;     		    // 차종( 소형 대형 어쩌구)
    private int caryear;                    //  -- 연식 
    private int mileage;                    // -- 주행거리 (숫자로 변경)
    private int price;                      // -- 가격
    private String condition;               //-- 상태(신, 중고)
    private String carimage;                //-- 사진 URL (길이 늘림)
    @Temporal(TemporalType.DATE)
    private Date cardate;                   //-- 등록일 (DATE 타입으로 변경)
    private int carhit;                     // -- 조회수
    private String sellerid;               //  -- 판매자 아이디
    @Column(name = "buyerid")
    private String buyerid;                //-- 구매자 아이디
    

}
