package com.collecting.collecting_data_news.api.mykeyword.repository;

import com.collecting.collecting_data_news.api.mykeyword.repository.querydsl.MyKeywordCustom;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mykeyword.entity.MyKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyKeywordRepository extends JpaRepository<MyKeyword, Long> , MyKeywordCustom {
    Optional<MyKeyword> findByMemberAndKeyword(Member member, Keyword keyword);
}
