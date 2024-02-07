package com.collecting.collecting_data_news.api.mykeyword.repository.querydsl;

import com.collecting.collecting_data_news.domain.mykeyword.dto.MyKeywordRespDto;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mykeyword.entity.MyKeyword;

import java.util.List;
import java.util.Optional;

public interface MyKeywordCustom {
    List<MyKeywordRespDto> myKeywordList(Member member);
    MyKeyword findByIdxAndMember(Long myKeywordIdx, Member member);
}
