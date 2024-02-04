package com.collecting.collecting_data_news.api.mykeyword.repository.querydsl;

import com.collecting.collecting_data_news.api.mykeyword.dto.MyKeywordRespDto;
import com.collecting.collecting_data_news.domain.member.entity.Member;

import java.util.List;

public interface MyKeywordCustom {
    List<MyKeywordRespDto> myKeywordList(Member member);
}
