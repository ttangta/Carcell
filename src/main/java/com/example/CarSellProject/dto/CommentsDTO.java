package com.example.CarSellProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Long num; // 댓글 식별자
    private String cmtcontent; // 댓글 내용

    // Request DTO (수정 요청에 대한 DTO)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long num; // 댓글 식별자
        private String cmtcontent; // 수정할 댓글 내용
    }
    
    // Response DTO (응답을 위한 DTO)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private boolean success; // 수정 성공 여부
        private String message; // 응답 메시지
        private String formattedContent;
    }
}



