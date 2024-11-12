package com.example.CarSellProject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.CarSellProject.dto.QnADTO;
import com.example.CarSellProject.entity.Comments;
import com.example.CarSellProject.entity.Qna;
import com.example.CarSellProject.repository.CommentsRepository;
import com.example.CarSellProject.repository.QnARepository;


@Repository
public class QnADAO {
	@Autowired
	private QnARepository qnarepository;
	@Autowired
	private CommentsRepository cmtRepository;

	// 게시물 목록
	public List<Qna> QnAList(int startNum, int endNum) {
		return qnarepository.findByStartnumAndEndnum(startNum, endNum);
	}

	// 게시물 개수
	public int getCount() {
		return (int) qnarepository.count();
	}
	
	// 게시물 검색
	public List<Qna> searchQnA(String search) {
	    return qnarepository.searchByNameOrSubject(search);
	}


	// 게시물 작성
	public Qna QnAWrite(QnADTO dto) {
		Qna qna = dto.toEntity();
		return qnarepository.save(qna);
	}

	// 상세보기
	public Qna QnAView(int seq) {
		return qnarepository.findById(seq).orElse(null);
	}

	// 조회수 증가
	public void incrementHit(int seq) {
		// 게시글 조회
		Qna qna = qnarepository.findById(seq).orElse(null);
		if (qna != null) {
			// 조회수 증가
			qna.setHit(qna.getHit() + 1);
			// 변경사항 저장
			qnarepository.save(qna);
		}
	}

	// 게시물 삭제
	public boolean QnADelete(int seq) {
		// 1. 삭제 대상 가져오기
		Qna qna = qnarepository.findById(seq).orElse(null);

		if (qna != null) {
			// 2. 댓글 삭제하기
			// 2. 댓글 조회하기
			List<Comments> comments = cmtRepository.findByPostOrderByCmtlogtimeDesc(qna);
			cmtRepository.deleteAll(comments);

			// 3. 게시물 삭제하기
			qnarepository.delete(qna);

		}
		return qnarepository.existsById(seq);
	}

	// 게시물 수정
	public boolean QnAModify(QnADTO dto) {
		// 1. 기존 데이터 가져오기
		Qna qna = qnarepository.findById(dto.getSeq()).orElse(null);

		if (qna != null) {
			// 2. 수정
			qna.setSubject(dto.getSubject());
			qna.setContent(dto.getContent());
			// 저장
			Qna qna_result = qnarepository.save(qna);
			boolean result = false;
			if (qna_result != null)
				result = true;
			return result;
		}
		return false;
	}
	
	
	/////////////////////////////////////////
	// 댓글

	// 댓글 추가
	public Comments addComments(int qna_seq, Comments comment) {
		Qna qna = QnAView(qna_seq); // QnA 객체를 가져옴
		comment.setPost(qna); // QnA 객체를 댓글에 설정
		return cmtRepository.save(comment); // 댓글 저장
	}

	// 대댓글 추가
	public Comments addReply(Long parentCommentId, Comments reply) {
	    Comments parentComment = cmtRepository.findById(parentCommentId)
	            .orElseThrow(() -> new RuntimeException("Parent comment not found"));
	    reply.setParentComment(parentComment);
	    return cmtRepository.save(reply);
	}

	// 댓글 날짜순으로 조회
	public List<Comments> OrderByQnAId(int qna_seq) {
		Qna qna = QnAView(qna_seq);
		return cmtRepository.findByPostOrderByCmtlogtimeDesc(qna); // 오름차순 정렬
	}

	// 특정 댓글의 대댓글 조회
	// 대댓글 목록을 계층적으로 조회
	public List<Comments> getReplies(Long parentCommentId) {
	    Comments parentComment = cmtRepository.findById(parentCommentId)
	            .orElseThrow(() -> new RuntimeException("Parent comment not found"));
	    return cmtRepository.findByParentCommentOrderByCmtlogtimeDesc(parentComment);
	}
	
	// 댓글 삭제
	public void CommentDelete(Long num) {
		Comments comment = cmtRepository.findById(num)
				.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
		// 댓글 삭제
		cmtRepository.delete(comment);
	}

	// 댓글 수정
	public void updateComment(Long num, String content) {
		Optional<Comments> optionalComment = cmtRepository.findById(num);
		if (optionalComment.isPresent()) {
			Comments comment = optionalComment.get();
			comment.setCmtcontent(content);
			cmtRepository.save(comment);
		} else {
			throw new RuntimeException("댓글을 찾을 수 없습니다.");
		}
	}
	
	


}
