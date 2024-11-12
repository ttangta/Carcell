package com.example.CarSellProject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.UserMemberDTO;
import com.example.CarSellProject.entity.Usermember;
import com.example.CarSellProject.repository.UsermemberRepository;


@Repository
public class UsermemberDAO {
	@Autowired
	UsermemberRepository usermemberRepository;
	
	// 아이디 중복확인
	public boolean idCheck(String id) {
		boolean result = false;
		Usermember usermember = usermemberRepository.findById(id).orElse(null);
		// 해당 아이디에 대한 결과가 없다면 true (사용가능 아이디)
		if(usermember == null) {
			result = true;
		}
		return result;
	}
	
	// 회원가입
	public boolean join(UserMemberDTO dto) {
		boolean result = false;
		Usermember usermember = dto.toEntity();
		Usermember joinmember = usermemberRepository.save(usermember);
		if(joinmember != null) {
			result = true;
		}
		return result;
	}
	
	// 로그인
	public Usermember login(UserMemberDTO dto) {
		return usermemberRepository.findByIdAndPw(dto.getId(), dto.getPw());
	}
	
	// 회원정보 가져오기 - 1
	public Usermember searchUser(UserMemberDTO dto) {
		return usermemberRepository.findById(dto.getId()).orElse(null);
	}
	
	// 회원정보 가져오기 - 2
	public Usermember searchUser1(String id) {
		return usermemberRepository.findById(id).orElse(null);
	}
	
	// 아아디 찾기 존재 회원 유무 확인
	public boolean findId(UserMemberDTO dto) {
		return usermemberRepository.findByNameAndEmail1AndEmail2(dto.getName(),dto.getEmail1(),dto.getEmail2()) != null;
	}
	
	// 아아디 보여주기
	public String viewId(UserMemberDTO dto) {
		return usermemberRepository.findByNameAndEmail1AndEmail2(dto.getName(), dto.getEmail1(), dto.getEmail2()).getId();
	}
	
	// 존재 여부
	public boolean realUser(Usermember user) {
		return usermemberRepository.existsById(user.getId());
	}
	
	// 비밀번호 재설정
	public boolean pwUpdate(Usermember user) {
		boolean result = false;
		Usermember beforUser = usermemberRepository.findById(user.getId()).orElse(null);
		if(beforUser != null) {
			Usermember updateUser = usermemberRepository.save(user);
			result = true;
		}
		return result;
	}
	
	// 회원정보 수정
	public boolean userUpdate(UserMemberDTO dto) {
		String id = dto.getId();
		Usermember update = dto.toEntity();
		boolean result = false;
		
		Usermember before = usermemberRepository.findById(id).orElse(null);
		
		if(before != null) {
			Usermember updateUser = usermemberRepository.save(update);
			result = true;
		}
		
		return result;
	}
	// 마이페이지
	public Usermember userView(String id) {
		return usermemberRepository.findById(id).orElse(null);
	}
	
	// 삭제하기
	public boolean deleteAcc(String id) {
	    // 해당 ID로 엔티티가 존재하는지 확인
	    if (usermemberRepository.existsById(id)) {
	        // 삭제하기
	        usermemberRepository.deleteById(id);
	        
	        // 삭제 후, 존재 여부 확인
	        return !usermemberRepository.existsById(id); // 삭제가 성공적으로 되었으면 true 반환
	    }
	    
	    return false; // ID가 존재하지 않으면 false 반환
	}

	
	
}
