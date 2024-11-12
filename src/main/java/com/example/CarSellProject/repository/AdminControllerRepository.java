package com.example.CarSellProject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.dto.AdmindetailDTO;
import com.example.CarSellProject.entity.Admincontroller;

public interface AdminControllerRepository  extends JpaRepository<Admincontroller, Integer>{
	
	
	// adc에 있는 타입, 해결여부에 맞는 차량 리스트 가저오기
	@Query(value = "select * from"
			 + "(select rownum rn, tt.* from"
			 + "(select * from admincontroller where type =:type and status =:status order by seq desc) tt)"
			 + "where rn>=:startNum and rn<=:endNum",
			 nativeQuery = true)
	List<Admincontroller> findByStartnumAndEndnum(@Param("startNum") int startNum,
												@Param("endNum") int endNum,
												@Param("type") String type,
												@Param("status") int status);
	
	// 타입,해결여부의 수량 가저오기
	@Query(value="select count(*) from (select rownum rn, tt.* from (select * from admincontroller where type =:type and status =:status order by seq desc) tt)",
			nativeQuery=true)
	int totalCount(@Param("type") String type,
					@Param("status") int status);		
	
	
	
	@Query(value="select "
			+ "seq,"
			+ "userid,"
			+ "shopid,"
			+ "carnum,"
			+ "time,"
			+ "type,"
			+ "status,"
			+ "shopname,"
			+ "modelname,"
			+ "carimage,"
			+ "condition"
			+ " from (select rownum rn, tt.* from "
			+ "(SELECT "
			+ " ac.seq,"
			+ " ac.userid,"
			+ " ac.shopid,"
			+ " ac.carnum,"
			+ " ac.time,"
			+ " ac.type,"
			+ " ac.status,"
			+ " s.shopname,"
			+ " c.modelname,"
			+ " c.carimage,"
			+ " c.condition "
			+ "FROM "
			+ " AdminController ac "
			+ "JOIN "
			+ " Shop s ON ac.shopid = s.shopid "
			+ "JOIN "
			+ " Car c ON ac.carnum = c.carnum "
			+ "WHERE "
			+ " ac.status = :status "
			+ "AND"
			+ " ac.type = :type order by seq desc) tt)where rn>=:startNum and rn<=:endNum",
			nativeQuery=true)
	List<AdmindetailDTO> findAdminList(@Param("startNum") int startNum,
												@Param("endNum") int endNum,
												@Param("type") String type,
												@Param("status") int status);
	
	@Query(value="select count(*) from (select rownum rn, tt.* from "
			+ "(SELECT "
			+ " ac.seq,"
			+ " ac.userid,"
			+ " ac.shopid,"
			+ " ac.carnum,"
			+ " ac.time,"
			+ " ac.type,"
			+ " ac.status,"
			+ " s.shopname,"
			+ " c.modelname,"
			+ " c.carimage "
			+ "FROM "
			+ " AdminController ac "
			+ "JOIN "
			+ " Shop s ON ac.shopid = s.shopid "
			+ "JOIN "
			+ " Car c ON ac.carnum = c.carnum "
			+ "WHERE "
			+ " ac.status = :status "
			+ "AND"
			+ " ac.type = :type order by seq desc) tt)",
			nativeQuery=true)
	int findAdminCount( @Param("type") String type,
						@Param("status") int status);
	
	
	@Query(value="SELECT "
	        + "ac.seq, ac.userid, ac.shopid, ac.carnum, ac.time, ac.type, ac.status, "
	        + "s.shopname, c.modelname, c.carimage, c.condition "
	        + "FROM AdminController ac "
	        + "JOIN Shop s ON ac.shopid = s.shopid "
	        + "JOIN Car c ON ac.carnum = c.carnum "
	        + "WHERE ac.status = :status AND ac.type = :type "
	        + "ORDER BY ac.seq DESC "
	        + "OFFSET :startNum -1 ROWS FETCH NEXT 5 ROWS ONLY",
	        nativeQuery=true)
	List<Map<String, Object>> findAdminListRaw(@Param("startNum") int startNum,
	                                            @Param("type") String type,
	                                            @Param("status") int status);
	
	
	
	// userid랑 타입이로 관련 값 가져오기
	@Query(value="select * from admincontroller where userid=:userid and type=:type order by seq",
			nativeQuery=true)
	List<Admincontroller> getListByUseridAndType(@Param("userid") String userid,
											@Param("type") String type);
	
	
	@Query(value="SELECT "
	        + "ac.seq, ac.userid, ac.shopid, ac.carnum, ac.time, ac.type, ac.status, "
	        + "s.shopname, c.modelname, c.carimage, c.condition "
	        + "FROM AdminController ac "
	        + "JOIN Shop s ON ac.shopid = s.shopid "
	        + "JOIN Car c ON ac.carnum = c.carnum "
	        + "WHERE ac.type = :type AND ac.userid =:userid "
	        + "ORDER BY ac.seq DESC ",
	        nativeQuery=true)
	List<Map<String, Object>> findUserSellList( @Param("userid") String userid,
	                                            @Param("type") String type);
	
	

	
}
