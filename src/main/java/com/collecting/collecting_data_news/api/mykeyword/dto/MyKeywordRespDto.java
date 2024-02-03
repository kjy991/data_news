package com.collecting.collecting_data_news.api.mykeyword.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MyKeywordRespDto {
    private Long idx;
    private Long keywordIdx;
    private String keyword;

    @QueryProjection
    public MyKeywordRespDto(Long idx, Long keywordIdx, String keyword) {
        this.idx = idx;
        this.keywordIdx = keywordIdx;
        this.keyword = keyword;
    }
}
