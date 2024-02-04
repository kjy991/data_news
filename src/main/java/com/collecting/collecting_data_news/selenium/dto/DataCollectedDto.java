package com.collecting.collecting_data_news.selenium.dto;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import lombok.Data;

@Data
public class DataCollectedDto {
    private String newspaper;
    private String title;
    private String href;
    private Long keywordIdx;
    private String keyword;
    private SearchNewspaper searchNewsPaper;

    public DataCollectedDto(String newspaper, String title, String href, KeywordDto keyword, SearchNewspaper searchNewsPaper) {
        this.newspaper = newspaper;
        this.title = title;
        this.href = href;
        this.keywordIdx = keyword.getIdx();
        this.keyword = keyword.getWord();
        this.searchNewsPaper = searchNewsPaper;
    }

    public DataCollectedDto(String newspaper, String title, String href, String keyword, SearchNewspaper searchNewsPaper) {
        this.newspaper = newspaper;
        this.title = title;
        this.href = href;
        this.keywordIdx = 1L;
        this.keyword = keyword;
        this.searchNewsPaper = searchNewsPaper;
    }
}
