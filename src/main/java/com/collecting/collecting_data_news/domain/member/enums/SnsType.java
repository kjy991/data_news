package com.collecting.collecting_data_news.domain.member.enums;

import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.CONVERT_ENUM_FAIL;


public enum SnsType  {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE");

    @JsonValue
    private final String label;

    SnsType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    @JsonCreator
    public static SnsType codeOf(String label) {
        return Arrays.stream(SnsType.values())
                .filter(a -> a.label.equals(label))
                .findAny()
                .orElseThrow(() -> new BusinessException(label + CONVERT_ENUM_FAIL));
    }
}



