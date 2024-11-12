package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Fuel;

public interface FuelRepository extends JpaRepository<Fuel, Integer>{
	
	
	@Query(value = "select * from fuel where carnum = :carnum",
			 nativeQuery = true)
	List<Fuel> FuelByCarnum(@Param("carnum") int carnum);
	
	
	
}
