package com.example.CarSellProject.model;

import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

public class Time {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String calculateTime(Date date) {
        // 현재 시간을 서울 시간대로 얻기 위해 Calendar를 사용
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        long curTime = calendar.getTimeInMillis(); // 현재 서버 시간 (서울 시간대)

        long regTime = date.getTime(); // 입력받은 날짜 (UTC)
        long diffTime = (curTime - regTime) / 1000; // 초 단위 차이 계산
        String msg = null;

        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = diffTime + "초 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }

        return msg;
    }
}
