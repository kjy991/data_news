package com.collecting.collecting_data_news.api.mykeyword.repository.querydsl;


import com.collecting.collecting_data_news.domain.mykeyword.dto.MyKeywordRespDto;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mykeyword.dto.QMyKeywordRespDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.collecting.collecting_data_news.domain.keyword.entity.QKeyword.keyword;
import static com.collecting.collecting_data_news.domain.member.entity.QMember.*;
import static com.collecting.collecting_data_news.domain.mykeyword.entity.QMyKeyword.myKeyword;


@RequiredArgsConstructor
public class MyKeywordCustomImpl implements MyKeywordCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyKeywordRespDto> myKeywordList(Member myMember) {
        return queryFactory
                .select(new QMyKeywordRespDto(
                        myKeyword.idx,
                        keyword.idx,
                        keyword.word
                ))
                .from(myKeyword)
                .innerJoin(keyword).on(keyword.eq(myKeyword.keyword))
                .innerJoin(member).on(member.eq(myKeyword.member))
                .fetch();
    }
}
