package com.example.CarSellProject.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CarSellProject.entity.Qna;

public interface QnARepository extends JpaRepository<Qna, Integer> {
    @Query(value = "select * from (select rownum rn, tt.* from (select * from qna order by seq desc) tt) where rn>=:startNum and rn<=:endNum",
           nativeQuery = true)
    List<Qna> findByStartnumAndEndnum(@Param("startNum") int startNum,
                                      @Param("endNum") int endNum);
    
    @Query("SELECT q FROM Qna q WHERE q.name LIKE %:search% OR q.subject LIKE %:search%")
    List<Qna> searchByNameOrSubject(@Param("search") String search);

    
   
}
