package com.collecting.collecting_data_news.api.keyword.repository.querydsl;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.domain.keyword.dto.QKeywordDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.collecting.collecting_data_news.domain.keyword.entity.QKeyword.keyword;
import static com.collecting.collecting_data_news.domain.member.entity.QMember.member;
import static com.collecting.collecting_data_news.domain.mykeyword.entity.QMyKeyword.myKeyword;
import static com.collecting.collecting_data_news.domain.mynews.entity.QMyNews.myNews;

@RequiredArgsConstructor
public class KeywordCustomImpl implements KeywordCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<KeywordDto> keywordDtoList(SearchNewspaper searchNewspaper) {
        return queryFactory.select(new QKeywordDto(
                        keyword
                ))
                .from(member)
                .innerJoin(member.myNews, myNews)
                .innerJoin(member.myKeywords, myKeyword)
                .innerJoin(myKeyword.keyword, keyword)
                .where(myNews.searchNewspaper.eq(searchNewspaper))
                .fetch();
    }
}
