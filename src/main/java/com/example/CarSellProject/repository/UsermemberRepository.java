package com.example.CarSellProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Usermember;


public interface UsermemberRepository extends JpaRepository<Usermember, String>{
	
	@Query(value = "select * from usermember where id=:id and pw=:pw",nativeQuery = true)
	Usermember findByIdAndPw(@Param("id") String id, @Param("pw") String pw);
	
	@Query(value = "select * from usermember where name=:name and email1=:email1 and email2=:email2", nativeQuery = true)
	Usermember findByNameAndEmail1AndEmail2(@Param("name") String name, @Param("email1") String email1, @Param("email2") String email2);
	
}
