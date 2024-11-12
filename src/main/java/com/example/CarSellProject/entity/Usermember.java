package com.example.CarSellProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usermember {
	private String name;
	@Id
	private String id;
	private String pw;
	private int type;
	private String email1;
	private String email2;
	private String tel1;
	private String tel2;
	private String tel3;
	private String addr;
}
