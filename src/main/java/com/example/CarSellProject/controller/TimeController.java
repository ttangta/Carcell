package com.example.CarSellProject.controller;

import org.springframework.web.bind.annotation.*;

import com.example.CarSellProject.model.Time;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
public class TimeController {

    @PostMapping("/calculate-time")
    public Map<String, String> calculateTime(@RequestBody Map<String, String> requestBody) {
        String clientTimeStr = requestBody.get("clientTime");
        Date clientTime = Date.from(Instant.parse(clientTimeStr));

        String timeDifference = Time.calculateTime(clientTime);  // Time 클래스의 calculateTime 메서드를 사용해 시간 계산

        return Map.of("timeDifference", timeDifference);  // 클라이언트로 응답
    }
}
