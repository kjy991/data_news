package com.collecting.collecting_data_news.api.keyword.repository.querydsl;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;

import java.util.List;

public interface KeywordCustom {
    List<KeywordDto> keywordDtoList(SearchNewspaper searchNewspaper);
}
