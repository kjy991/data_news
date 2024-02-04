package com.collecting.collecting_data_news.api.keyword.dto;

import com.collecting.collecting_data_news.domain.keyword.Keyword;
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

    public KeywordDto(Keyword keyword) {
        this.idx = keyword.getIdx();
        this.word = keyword.getWord();
    }

    public KeywordDto(KeywordDto keywordDto) {
        this.idx = keywordDto.idx;
        this.word = keywordDto.word;
    }
}
