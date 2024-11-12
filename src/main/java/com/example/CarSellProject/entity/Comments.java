package com.example.CarSellProject.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.CarSellProject.model.Time;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "COMMENTS_SEQUENCE_GENERATOR", sequenceName = "seq_comments", initialValue = 1, allocationSize = 1)
    private Long num;  // 댓글의 고유 식별자

    @Column(name = "cmtcontent", nullable = false, length = 255)
    private String cmtcontent;  // 댓글 내용

    @Temporal(TemporalType.TIMESTAMP)  // 날짜와 시간을 포함
    @Column(name = "cmtlogtime")
    private Date cmtlogtime;  // 댓글 작성 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmtid")
    private Usermember user_id;  // 작성자 정보, member 테이블의 id를 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmtseq")
    private Qna post;  // 게시물 정보, qna 테이블의 seq를 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentnum")
    private Comments parentComment;  // 대댓글을 위한 자기 참조 외래키
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> replies = new ArrayList<>();
    
    // cmtlogtime을 "X 시간 전" 같은 형식으로 반환하는 메서드 추가
    public String getFormattedCmtlogtime() {
        return Time.calculateTime(cmtlogtime);
    }
    
    @Override
    public String toString() {
        return "Comments{" +
                "num=" + num +
                ", cmtcontent='" + cmtcontent + '\'' +
                ", cmtlogtime=" + cmtlogtime +
                ", user_id=" + (user_id != null ? user_id.getId() : "null") +
                ", post=" + (post != null ? post.getSeq() : "null") +
                ", parentComment=" + (parentComment != null ? parentComment.getNum() : "null") +
                '}';
    }
}
