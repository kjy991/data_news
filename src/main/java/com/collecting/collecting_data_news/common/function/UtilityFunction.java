package com.collecting.collecting_data_news.common.function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class UtilityFunction {
    // 허용된 문자 패턴 정의 (영문자, 숫자, 공백)
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s가-힣]+$");

    public static boolean isValidString(String input) {
        // 빈 문자열 확인
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        // 특수 문자 확인
        if (!ALLOWED_PATTERN.matcher(input).matches()) {
            return false;
        }
        return true;
    }

    public static String format_yyyy_MM_dd_HH_mm_ss(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .orElse(null);
    }

    public static String format_yyyy_MM_dd(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElse(null);
    }

}
