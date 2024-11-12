package com.example.CarSellProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.entity.Comments;
import com.example.CarSellProject.entity.Qna;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

	// 날짜 순으로 댓글 정렬
	List<Comments> findByPostOrderByCmtlogtimeDesc(Qna post);
    
    // 대댓글 조회 및 정렬
    List<Comments> findByParentCommentOrderByCmtlogtimeDesc(Comments parentComment);

}

