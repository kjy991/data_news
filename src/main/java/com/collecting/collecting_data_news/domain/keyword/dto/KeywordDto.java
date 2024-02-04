package com.collecting.collecting_data_news.domain.keyword.dto;

import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KeywordDto {
    private Long idx;
    private String word;

    public KeywordDto(Long idx, String word) {
        this.idx = idx;
        this.word = word;
    }

    @QueryProjection
    public KeywordDto(Keyword keyword) {
        this.idx = keyword.getIdx();
        this.word = keyword.getWord();
    }

}
