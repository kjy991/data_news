package com.collecting.collecting_data_news.api.mykeyword.repository.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyKeywordCustomImpl implements MyKeywordCustom {
    private final JPAQueryFactory queryFactory;
}
