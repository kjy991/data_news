package com.collecting.collecting_data_news.api.member.repository;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.member.enums.SnsType;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.collecting.collecting_data_news.domain.member.entity.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findBySnsIdAndSnsType(String snsId, SnsType snsType) {
        return null;
//        return Optional.ofNullable(queryFactory
//                .select(member)
//                .from(member)
//                .where(
//                        member.snsType.eq(snsType),
//                        member.snsId.eq(snsId)
//                )
//                .fetchOne())
//                ;
    }

}