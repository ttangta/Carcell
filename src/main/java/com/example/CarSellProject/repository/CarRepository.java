package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Car;


public interface CarRepository extends JpaRepository<Car, Integer>{
	
	@Query(value = "select * from"
			 + "(select rownum rn, tt.* from"
			 + "(select * from car where condition in(:condition) AND price != 0 order by carnum desc) tt)"
			 + "where rn>=:startNum and rn<=:endNum",
			 nativeQuery = true)
	List<Car> findByStartnumAndEndnumOld(@Param("startNum") int startNum,
									@Param("endNum") int endNum,
									@Param("condition") List<String> condition);
	
	
	@Query(value = "select * from"
			 + "(select rownum rn, tt.* from"
			 + "(select * from car where condition in(:condition) AND price != 0 order by carnum desc) tt)"
			 + "where rn>=:startNum and rn<=:endNum",
			 nativeQuery = true)
	List<Car> findByStartnumAndEndnumNew(@Param("startNum") int startNum,
									@Param("endNum") int endNum,
									@Param("condition") List<String> condition);
	
	
	

	@Query(value= "SELECT * FROM (" +
				"SELECT c.*, rownum rn FROM (" + 
				"SELECT c.*, ROW_NUMBER() OVER (PARTITION BY c.carnum ORDER BY c.carnum DESC) as row_num " +
				"FROM car c JOIN fuel f ON c.carnum = f.carnum " + 
				"WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
				"AND (:maxPrice = 0 OR c.price <= :maxPrice) " +
                "AND (:makersNullCheck IS NULL OR c.maker IN (:makers)) " +
                "AND (:sizesNullCheck IS NULL OR c.carsize IN (:carSize)) " +
                "AND (:fuelsNullCheck IS NULL OR f.fueltype IN (:fuelTypes)) " +
				"AND (c.condition IN (:condition)) " +
				"AND price != 0 " +
				"ORDER BY c.carnum DESC) c " +
				"WHERE row_num = 1" +
				" ) WHERE rn >=:startNum AND rn <=:endNum",
				nativeQuery = true)
	List<Car> hardsearch(
	    @Param("minPrice") Integer minPrice, 
	    @Param("maxPrice") Integer maxPrice, 
	    @Param("makers") List<String> makers, 
	    @Param("carSize") List<String> carSize, 
	    @Param("fuelTypes") List<String> fuelTypes,
	    @Param("startNum") int startNum,
		@Param("endNum") int endNum,
		@Param("condition") List<String> condition,
		@Param("makersNullCheck") String makersNullCheck,
		@Param("sizesNullCheck") String sizesNullCheck,
		@Param("fuelsNullCheck") String fuelsNullCheck);
	
	
	
	// 카운트
	@Query(value= "SELECT COUNT(*) FROM (" +
            "SELECT c.*, ROW_NUMBER() OVER (PARTITION BY c.carnum ORDER BY c.carnum DESC) as row_num " +
            "FROM car c JOIN fuel f ON c.carnum = f.carnum " + 
            "WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
            "AND (:maxPrice = 0 OR c.price <= :maxPrice) " +
            "AND (:makersNullCheck IS NULL OR c.maker IN (:makers)) " +
            "AND (:sizesNullCheck IS NULL OR c.carsize IN (:carSize)) " +
            "AND (:fuelsNullCheck IS NULL OR f.fueltype IN (:fuelTypes)) " +
            "AND (c.condition IN (:condition)) " +
            ") c " +
            "WHERE row_num = 1  AND price != 0", 
            nativeQuery = true)
	int newcarListCount(
	    @Param("minPrice") Integer minPrice, 
	    @Param("maxPrice") Integer maxPrice, 
	    @Param("makers") List<String> makers, 
	    @Param("carSize") List<String> carSize, 
	    @Param("fuelTypes") List<String> fuelTypes,
		@Param("condition") List<String> condition,
		@Param("makersNullCheck") String makersNullCheck,
		@Param("sizesNullCheck") String sizesNullCheck,
		@Param("fuelsNullCheck") String fuelsNullCheck);
	
	// 카운트
	@Query(value= "SELECT COUNT(*) FROM (" +
            "SELECT c.*, ROW_NUMBER() OVER (PARTITION BY c.carnum ORDER BY c.carnum DESC) as row_num " +
            "FROM car c JOIN fuel f ON c.carnum = f.carnum " + 
            "WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
            "AND (:maxPrice = 0 OR c.price <= :maxPrice) " +
            "AND (:makersNullCheck IS NULL OR c.maker IN (:makers)) " +
            "AND (:sizesNullCheck IS NULL OR c.carsize IN (:carSize)) " +
            "AND (:fuelsNullCheck IS NULL OR f.fueltype IN (:fuelTypes)) " +
            "AND ( c.condition IN (:condition)) " +
            ") c " +
            "WHERE row_num = 1  AND price != 0", 
            nativeQuery = true)
	int oldcarListCount(
	    @Param("minPrice") Integer minPrice, 
	    @Param("maxPrice") Integer maxPrice, 
	    @Param("makers") List<String> makers, 
	    @Param("carSize") List<String> carSize, 
	    @Param("fuelTypes") List<String> fuelTypes,
		@Param("condition") List<String> condition,
		@Param("makersNullCheck") String makersNullCheck,
		@Param("sizesNullCheck") String sizesNullCheck,
		@Param("fuelsNullCheck") String fuelsNullCheck);
	
	
	
	// 검색 파트---------------------
	// 검색				  AND price != 0
	@Query(value="select * from (select rownum rn, tt.* from"
			+ "(select * from car where (maker like:search or modelname like:search)  AND price != 0 order by carnum desc)"
			+ " tt)where rn>=:startNum and rn<=:endNum"
			,nativeQuery = true)
	List<Car> search(@Param("search") String search,
					 @Param("startNum") int startNum,
					 @Param("endNum") int endNum);
	

	// 검색 조건에 맞는 데이터 총 개수 구하기
	@Query(value = "select count(*) as total from car where (maker like:search or modelname like:search) AND price != 0",nativeQuery = true)
	int searchCount(@Param("search") String search);
	
	
	// (관리자 영역)등록된 차량 확인 (가격이 0인 목록)
	@Query(value = "select * from(select rownum rn, tt.* from(select * from car where price = 0 order by carnum desc) tt)where rn>=:startNum and rn<=:endNum",nativeQuery = true) 
	List<Car> registerCar(@Param("startNum") int startNum,
						  @Param("endNum") int endNum);
	
	// (관리자 영역) 등록된 차량의 갯수 (가격이 0인 데이터 갯수)
	@Query(value ="select count(*) as total from car where price=0",nativeQuery = true)
	int admintotal();
	
	// 아이디로 등록차량 검색
	@Query(value="select * from car where sellerid=:id",nativeQuery = true)
	List<Car> myCar(@Param("id") String id);

	// 신규차량 5개 가져오기
	@Query(value="select * from(select rownum rn, tt.* from(select * from car where condition='new' order by carnum desc) tt)where rn>=:startNum and rn<=:endNum",nativeQuery = true)
	List<Car> mainList(@Param("startNum") int startNum,
					   @Param("endNum") int endNum);
	
	// 가격 측정된 중고차 10개 가져오기
	@Query(value = "select * from(select rownum rn, tt.* from(select * from car where condition='old' and price != 0 order by carnum desc)tt)where rn>=1 and rn<=9",nativeQuery = true)
	List<Car> oldCarList();	

	
}
