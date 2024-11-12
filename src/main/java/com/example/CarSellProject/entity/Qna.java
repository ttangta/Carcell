package com.example.CarSellProject.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Qna {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QNA_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "QNA_SEQUENCE_GENERATOR", sequenceName = "seq_qna", initialValue = 1, allocationSize = 1)
	private int seq;
	private String id;
	private String name;
	private String subject;
	private String content;
	private int hit;
	@Temporal(TemporalType.DATE)
	private Date logtime;
	
	// 게시물의 기본키를 통해 댓글목록을 가져옴
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comments> comments;
}
