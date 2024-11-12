package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Event;


public interface EventRepository 
		extends JpaRepository<Event, Integer>{
	@Query(value = "select * from "
				 + " (select rownum rn, tt.* from "
			     + " (select * from event order by seq desc) tt) "
			     + " where rn>=:startNum and rn<=:endNum ",
					nativeQuery = true)
	List<Event> findByStartnumAndEndnum(@Param("startNum") int startNum,
											 @Param("endNum") int endNum);

}
