package com.collecting.collecting_data_news.api.mynews.repository.querydsl;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.dto.MyNewsRespDto;
import com.collecting.collecting_data_news.domain.mynews.dto.QMyNewsRespDto;
import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import com.collecting.collecting_data_news.domain.mynews.entity.QMyNews;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.collecting.collecting_data_news.domain.member.entity.QMember.member;
import static com.collecting.collecting_data_news.domain.mynews.entity.QMyNews.*;

@RequiredArgsConstructor
public class MyNewsCustomImpl implements MyNewsCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MyNews> findByMemberAndSearchNewsPaper(Member myMember, SearchNewspaper searchNewspaper) {
        return Optional.ofNullable(queryFactory
                .select(myNews)
                .from(myNews)
                .innerJoin(myNews.member, member)
                .where(
                        member.eq(myMember),
                        myNews.searchNewspaper.eq(searchNewspaper)
                )
                .fetchOne());
    }

    @Override
    public List<MyNewsRespDto> myNewsList(Member myMember) {
        return queryFactory
                .select(new QMyNewsRespDto(
                        myNews
                ))
                .from(myNews)
                .innerJoin(myNews.member, member)
                .fetch();
    }
}
