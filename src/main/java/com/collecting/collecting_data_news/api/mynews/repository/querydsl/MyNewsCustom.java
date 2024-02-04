package com.collecting.collecting_data_news.api.mynews.repository.querydsl;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.dto.MyNewsRespDto;
import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyNewsCustom {
    Optional<MyNews> findByMemberAndSearchNewsPaper(Member member, SearchNewspaper searchNewspaper);
    List<MyNewsRespDto> myNewsList(Member member);
}
