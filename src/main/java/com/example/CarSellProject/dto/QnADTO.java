package com.example.CarSellProject.dto;

import java.util.Date;

import com.example.CarSellProject.entity.Qna;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QnADTO {
    private int seq;
    private String id;
    private String name;
    private String subject;
    private String content;
    private int hit;
    private Date logtime;
    
    // QnADTO를 QnA 엔티티로 변환하는 메서드
    public Qna toEntity(){
        return new Qna(seq, id, name, subject, content, hit, logtime, null);
    }
}
