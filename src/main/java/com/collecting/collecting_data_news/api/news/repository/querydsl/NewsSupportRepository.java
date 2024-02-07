package com.collecting.collecting_data_news.api.news.repository.querydsl;

import com.collecting.collecting_data_news.common.support.Querydsl4RepositorySupport;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.domain.news.News;
import com.collecting.collecting_data_news.domain.news.dto.NewsRespDto;
import com.collecting.collecting_data_news.domain.news.dto.QNewsRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.collecting.collecting_data_news.domain.keyword.entity.QKeyword.*;
import static com.collecting.collecting_data_news.domain.member.entity.QMember.member;
import static com.collecting.collecting_data_news.domain.mykeyword.entity.QMyKeyword.*;
import static com.collecting.collecting_data_news.domain.mynews.entity.QMyNews.myNews;
import static com.collecting.collecting_data_news.domain.news.QNews.news;

@Repository
public class NewsSupportRepository extends Querydsl4RepositorySupport {
    public NewsSupportRepository() {
        super(News.class);
    }

    public Page<NewsRespDto> findNewsRespDtoList(Member myMember, SearchNewspaper searchNewspaper, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QNewsRespDto(
                        news.idx,
                        keyword.word,
                        news.searchNewspaper,
                        news.newspaper,
                        news.title,
                        news.href
                ))
                .from(member)
                .innerJoin(member.myKeywords, myKeyword)
                .innerJoin(keyword).on(keyword.eq(myKeyword.keyword))
                .innerJoin(member.myNews, myNews)
                .innerJoin(news).on(news.keyword.eq(myKeyword.keyword).and(news.searchNewspaper.eq(myNews.searchNewspaper)))
                .where(
                        member.eq(myMember),
                        news.searchNewspaper.eq(searchNewspaper)

                )
        );
    }

}
