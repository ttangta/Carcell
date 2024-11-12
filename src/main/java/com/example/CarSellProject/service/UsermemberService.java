package com.example.CarSellProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarSellProject.dao.UsermemberDAO;
import com.example.CarSellProject.dto.UserMemberDTO;
import com.example.CarSellProject.entity.Usermember;


@Service
public class UsermemberService {
	@Autowired
	UsermemberDAO dao;
	
	// 아이디 중복확인
	public boolean idCheck(String id) {
		return dao.idCheck(id);
	}
	
	// 회원가입
	public boolean join(UserMemberDTO dto) {
		return dao.join(dto);
	}
	
	// 로그인
	public Usermember login(UserMemberDTO dto) {
		return dao.login(dto);
	}
	
	// 회원정보 가져오기 - 1
	public Usermember searchUser(UserMemberDTO dto) {
		return dao.searchUser(dto);
	}
	
	// 회원정보 가져오기 - 2
	public Usermember searchUser1(String id) {
		return dao.searchUser1(id);
	}
	
	// 아아디 찾기 존재 회원 유무 확인
	public boolean findId(UserMemberDTO dto) {
		return dao.findId(dto);
	}
	
	// 아이디 보여주기
	public String viewId(UserMemberDTO dto) {
		return dao.viewId(dto);
	}
	
	// 존재여부
	public boolean realUser(Usermember user) {
		return dao.realUser(user);
	}
	
	// 비밀번호 재설정
	public boolean pwUpdate(Usermember user) {
		return dao.pwUpdate(user);
	}
	
	// 회원정보 수정
	public boolean userUpdate(UserMemberDTO dto) {
		return dao.userUpdate(dto);
	}
	
	// 마이페이지
	public Usermember userView(String id) {
		return dao.userView(id);
	}
	
	// 회원탈퇴 가 맞을까 계정삭제가 맞을까
	public boolean deleteAcc(String id) {
		return dao.deleteAcc(id);
	}
	
	
}
