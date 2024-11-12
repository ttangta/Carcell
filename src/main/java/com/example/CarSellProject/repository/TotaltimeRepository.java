package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Totaltime;

public interface TotaltimeRepository extends JpaRepository<Totaltime, Integer>{
	@Query(value = "select * from totaltime where id=:id",nativeQuery = true)
	List<Totaltime> times(@Param("id") String id);
	
	@Query(value = "select * from totaltime where carnum=:carnum",nativeQuery = true)
	Totaltime time(@Param("carnum") int carnum);
}
