package com.example.CarSellProject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Admincontroller {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "ADMIN_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "ADMIN_SEQUENCE_GENERATOR",
						sequenceName="seq_admin",
						initialValue = 1, allocationSize = 1)
    private int seq;			      // 관리번호
    private String userid;            // 사용자id
    private int shopid;               // 판매점id
    private int carnum;               // 차량번호
    private String time;                // 시간
    private String type;              // 사용자구매,사용자판매
    private int status;				  // 해결 미해결 식별코드
}
