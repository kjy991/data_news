package com.collecting.collecting_data_news.domain.member.enums;

import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.CONVERT_ENUM_FAIL;


public enum OsType {
    IOS("IOS"),
    AOS("AOS"),
    WEB("WEB"),
    ALL("ALL");


    @JsonValue
    private final String label;

    OsType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    @JsonCreator
    public static OsType codeOf(String label) {
        return Arrays.stream(OsType.values())
                .filter(a -> a.label.equals(label))
                .findAny()
                .orElseThrow(() -> new BusinessException(label + CONVERT_ENUM_FAIL));
    }
}



