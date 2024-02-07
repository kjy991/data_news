package com.collecting.collecting_data_news.domain.news.dto;

import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NewsRespDto {
    private Long idx;
    private String  keyword;
    private SearchNewspaper searchNewspaper;
    private String newspaper;
    private String title;
    private String href;

    @QueryProjection
    public NewsRespDto(Long idx, String keyword, SearchNewspaper searchNewspaper, String newspaper, String title, String href) {
        this.idx = idx;
        this.keyword = keyword;
        this.searchNewspaper = searchNewspaper;
        this.newspaper = newspaper;
        this.title = title;
        this.href = href;
    }
}
