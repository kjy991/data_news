package com.collecting.collecting_data_news.domain.member.enums;

import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.CONVERT_ENUM_FAIL;


public enum Grade  {
    QUIT("QUIT", 0), // 탈퇴자
    ROLE_SUSPENDED("ROLE_SUSPENDED", 1), // 영구 정지
    ROLE_ANONYMOUS("ROLE_ANONYMOUS", 1), // 게스트
    ROLE_HOT_DEAL_SUSPENDED("ROLE_HOT_DEAL_SUSPENDED", 2), // 핫딜 정지
    ROLE_MEMBER("ROLE_MEMBER", 3), // 유저
    ROLE_SUPPORTS("ROLE_SUPPORTS", 4), // 서포터즈
    ROLE_ADMIN("ROLE_ADMIN", 5), // 관리자
    ;

    @JsonValue
    private final String label;
    private final int level;

    Grade(String label, int level) {
        this.label = label;
        this.level = level;
    }

    private int level() {
        return level;
    }

    public String label() {
        return label;
    }

    @JsonCreator
    public static Grade codeOf(String label) {
        return Arrays.stream(Grade.values())
                .filter(a -> a.label.equals(label))
                .findAny()
                .orElseThrow(() -> new BusinessException(label + CONVERT_ENUM_FAIL));
    }
}



