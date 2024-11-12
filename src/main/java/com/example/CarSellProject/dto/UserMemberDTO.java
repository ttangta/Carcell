package com.example.CarSellProject.dto;


import com.example.CarSellProject.entity.Usermember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserMemberDTO {
	private String name;
	private String id;
	private String pw;
	private int type;
	private String email1;
	private String email2;
	private String tel1;
	private String tel2;
	private String tel3;
	private String addr;
	
	public Usermember toEntity() {
		return new Usermember(name, id, pw, type, email1, email2, tel1, tel2, tel3, addr);
	}
}
