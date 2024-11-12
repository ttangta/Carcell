package com.example.CarSellProject.dto;

import java.util.Date;

import com.example.CarSellProject.entity.Admincontroller;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminControllerDTO {
    private int seq;			      // 관리번호
    private String userid;            // 사용자id
    private int shopid;               // 판매점id
    private int carnum;               // 차량번호
    private String time;                // 시간
    private String type;              // 사용자구매,사용자판매
    private int status;				  //	 해결 미해결 식별코드
    
    public Admincontroller toEntity() {
    	return new Admincontroller(seq, userid, shopid, carnum, time, type, status);
    }
}
