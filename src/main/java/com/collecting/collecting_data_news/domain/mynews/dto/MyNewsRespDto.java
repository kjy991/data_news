package com.collecting.collecting_data_news.domain.mynews.dto;

import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MyNewsRespDto {
    private Long idx;
    private SearchNewspaper searchNewspaper;

    @QueryProjection
    public MyNewsRespDto(MyNews myNews) {
        this.idx = myNews.getIdx();
        this.searchNewspaper = myNews.getSearchNewspaper();
    }
}
