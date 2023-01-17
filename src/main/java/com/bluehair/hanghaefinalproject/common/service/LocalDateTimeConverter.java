package com.bluehair.hanghaefinalproject.common.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeConverter {
    public static String timeToString(LocalDateTime time) {
        String convertedTime = "";
        Duration duration = Duration.between(time, LocalDateTime.now());
        long differ = duration.getSeconds();
        long sec = differ % 60;
        long min = (differ / 60) % 60;
        long hour = (differ / (60 * 60)) % 60;
        long day = (differ / (60 * 60 * 60)) % 24;
        long year = (differ / (60 * 60 * 60 * 24)) % 365;
        if (year > 0) {
            convertedTime = convertedTime + year + "년 ";
            return convertedTime + "전";
        }
        if (day > 0) {
            convertedTime = convertedTime + day + "일 ";
            return convertedTime + "전";
        }
        if (hour > 0) {
            convertedTime = convertedTime + hour + "시간 ";
            return convertedTime + "전";
        }
        if (min > 0) {
            convertedTime = convertedTime + min + "분 ";
            return convertedTime + "전";
        }
        if (sec > 0) {
            convertedTime = convertedTime + sec + "초 ";
            return convertedTime + "전";
        }
        return "0초 전";
    }

    public static String timeToString8digits(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
