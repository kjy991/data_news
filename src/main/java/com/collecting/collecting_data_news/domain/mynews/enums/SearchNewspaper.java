package com.collecting.collecting_data_news.domain.mynews.enums;

import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.CONVERT_ENUM_FAIL;


public enum SearchNewspaper {
    NAVER("NAVER", "네이버"),
    DAUM("DAUM", "다음"),
    ;

    @JsonValue
    private final String label;

    @Getter
    private final String name;

    SearchNewspaper(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public String label() {
        return label;
    }

    @JsonCreator
    public static SearchNewspaper codeOf(String label) {
        return Arrays.stream(SearchNewspaper.values())
                .filter(a -> a.label.equals(label))
                .findAny()
                .orElseThrow(() -> new BusinessException(label + CONVERT_ENUM_FAIL));
    }
}



