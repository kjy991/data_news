package com.collecting.collecting_data_news.domain.member.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripcoach.core.common.enumType.Constant;
import com.tripcoach.core.common.exception.exception.BusinessException;

import java.util.Arrays;

import static com.tripcoach.core.common.apiresult.comcode.ComCode.CONVERT_ENUM_FAIL;


public enum SnsType implements Constant {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE");


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



