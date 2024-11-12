package com.example.CarSellProject.dao;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.AdminControllerDTO;
import com.example.CarSellProject.dto.AdmindetailDTO;
import com.example.CarSellProject.entity.Admincontroller;
import com.example.CarSellProject.repository.AdminControllerRepository;

@Repository
public class AdminControllerDAO {
	
	@Autowired
	AdminControllerRepository adminControllerRepository;
	
		public boolean userSell(AdminControllerDTO dto) {
		    // DTO를 엔티티로 변환
		    Admincontroller adminController = dto.toEntity();
		    
		    try {
		        adminControllerRepository.save(adminController);
		        return true; // 저장 성공
		    } catch (Exception e) {
		        // 예외 처리 로직
		    	System.out.println("저장 실패임 :(");
		        return false; // 저장 실패
		    }
		    
		}
		
	public List<AdmindetailDTO> getList(int startNum, int endNum, String type, int status){
		
		return adminControllerRepository.findAdminList(startNum,endNum,type,status);
	}
	
	public int totalCount(String type, int status) {
		return adminControllerRepository.findAdminCount(type, status);
	}
	
	//신규 구매 신청?리스트 
	public List<AdmindetailDTO> test1(int startNum, String type, int status) {
	    List<Map<String, Object>> results = adminControllerRepository.findAdminListRaw(startNum, type, status);

	    return results.stream()
	        .map(result -> {
	                return new AdmindetailDTO(
	                    ((BigDecimal) result.get("seq")).intValue(),
	                    (String) result.get("userid"),
	                    ((BigDecimal) result.get("shopid")).intValue(),
	                    ((BigDecimal) result.get("carnum")).intValue(),
	                    (String) result.get("time"),  
	                    (String) result.get("type"),
	                    ((BigDecimal) result.get("status")).intValue(),
	                    (String) result.get("shopname"),
	                    (String) result.get("modelname"),
	                    (String) result.get("carimage"),
	                    (String) result.get("condition")
	                );
	        })
	        .collect(Collectors.toList());
	}
	
	public Admincontroller getOne(int seq) {
		
		return adminControllerRepository.findById(seq).orElse(null);
	}
	
	
	// 해결로 수정하기
	public boolean update(int seq) {
		
		boolean result = false;
		
		Admincontroller admin = adminControllerRepository.findById(seq).orElse(null);
		
	    if (admin != null) {
	        // 특정 컬럼을 변경합니다. 예: admin.setColumnName(newValue);
	        admin.setStatus(1);

	        // 변경된 admin 객체를 저장합니다.
	        adminControllerRepository.save(admin);
	        result = true; // 성공적으로 업데이트된 경우 true 반환
	    }
		
		return result;
				
	}
	// 삭제한 척 하기
	public boolean updateDelete(int seq) {
		
		boolean result = false;
		
		Admincontroller admin = adminControllerRepository.findById(seq).orElse(null);
		
	    if (admin != null) {
	        // 특정 컬럼을 변경합니다. 예: admin.setColumnName(newValue);
	        admin.setStatus(3);

	        // 변경된 admin 객체를 저장합니다.
	        adminControllerRepository.save(admin);
	        result = true; // 성공적으로 업데이트된 경우 true 반환
	    }
		
		return result;
				
	}
	
	//사용자 아이디로 관련 리스트 가저오기
	public List<Admincontroller> getListByUser(String userid, String type){
		
		return adminControllerRepository.getListByUseridAndType(userid, type);
		
	}
	
	
	
	// 사용자 아이디로 판매 현황? 가저오기 
	public List<AdmindetailDTO> SellListByuserid(String userid, String type) {
	    List<Map<String, Object>> results = adminControllerRepository.findUserSellList(userid, type);

	    return results.stream()
	        .map(result -> {
	                return new AdmindetailDTO(
	                    ((BigDecimal) result.get("seq")).intValue(),
	                    (String) result.get("userid"),
	                    ((BigDecimal) result.get("shopid")).intValue(),
	                    ((BigDecimal) result.get("carnum")).intValue(),
	                    (String) result.get("time"),  
	                    (String) result.get("type"),
	                    ((BigDecimal) result.get("status")).intValue(),
	                    (String) result.get("shopname"),
	                    (String) result.get("modelname"),
	                    (String) result.get("carimage"),
	                    (String) result.get("condition")
	                );
	        })
	        .collect(Collectors.toList());
	}
	
	

}
