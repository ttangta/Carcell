package com.example.CarSellProject.dto;


import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class AdmindetailDTO {
	
    private int seq;			      // 관리번호
    private String userid;            // 사용자id
    private int shopid;               // 판매점id
    private int carnum;               // 차량번호
    private String time;                // 시간
    private String type;              // 사용자구매,사용자판매
    private int status;				  //	 해결 미해결 식별코드
    private String shopname;
    private String modelname;
    private String carimage;
    private String condition;
    
    
    public AdmindetailDTO(int seq, String userid, int shopid, int carnum, String time, String type, int status, String shopname, String modelname, String carimage,String condition) {
        this.seq = seq;
        this.userid = userid;
        this.shopid = shopid;
        this.carnum = carnum;
        this.time = time;
        this.type = type;
        this.status = status;
        this.shopname = shopname;
        this.modelname = modelname;
        this.carimage = carimage;
        this.condition = condition;
    }


    
    
    
    
}
