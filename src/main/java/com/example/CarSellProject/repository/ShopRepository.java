package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer>{
	
	
	// 중복도 가저오기
	
	@Query(value = "SELECT s.*"
			+ "	FROM shop s"
			+ "	JOIN shopcar sc ON s.shopid = sc.shopid"
			+ "	WHERE sc.carnum =:carnum",
			 nativeQuery = true)
	List<Shop> findShop(@Param("carnum") int carnum);
	
	
	@Query(value = "SELECT s.*"
			+ "	FROM shop s"
			+ "	JOIN shopcar sc ON s.shopid = sc.shopid"
			+ "	WHERE sc.carnum =:carnum",
			 nativeQuery = true)
	Shop findShopOldCar(@Param("carnum") int carnum);
	
	
	
	
	
	
	// 선택한 지역명으로 해당 판매점 db 가저오기
	@Query(value = "select shopname from"
				+ "	(SELECT s.*"
				+ "	FROM shop s"
				+ "	JOIN shopcar sc ON s.shopid = sc.shopid"
				+ "	WHERE sc.carnum =:carnum)"
				+ "	where location =:location",
				 nativeQuery = true)
	List<String> findShopfull(@Param("carnum") int carnum,
							@Param("location") String location);
	
	
	
	
	
	// 지역명만 가저오기
	@Query(value = "SELECT *"
			+ "	FROM shop s"
			+ "	WHERE s.shopid IN ("
			+ " SELECT MIN(s2.shopid)"
			+ " FROM shop s2"
			+ " JOIN shopcar sc ON s2.shopid = sc.shopid"
			+ " WHERE sc.carnum = :carnum"
			+ " GROUP BY s2.location)",
			 nativeQuery = true)
	List<Shop> findLocation(@Param("carnum") int carnum);
	
	
	
	// 상점이름으로 상점 식별코드 가져오기
	
	  @Query(value ="SELECT shopid FROM shop WHERE shopname = :shopname",
			  nativeQuery = true)
	  int getIdByShopname(@Param("shopname") String shopname);
	
	
	// 선택한 지역명으로 해당 판매점 이름 가저오기
	@Query(value = "select shopname from shop"
					+ "	where location =:location",
					 nativeQuery = true)
	List<String> findShopByname(@Param("location") String location);
	
	
	
	
	

}
