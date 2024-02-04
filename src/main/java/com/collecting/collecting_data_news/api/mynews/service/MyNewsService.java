package com.collecting.collecting_data_news.api.mynews.service;

import com.collecting.collecting_data_news.api.keyword.service.KeywordService;
import com.collecting.collecting_data_news.api.mykeyword.repository.MyKeywordRepository;
import com.collecting.collecting_data_news.api.mynews.repository.MyNewsRepository;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.collecting.collecting_data_news.common.function.AuthFunction;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mykeyword.dto.MyKeywordRespDto;
import com.collecting.collecting_data_news.domain.mykeyword.entity.MyKeyword;
import com.collecting.collecting_data_news.domain.mynews.dto.MyNewsRespDto;
import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.*;
import static com.collecting.collecting_data_news.common.apiresult.response.ApiResponse.success;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MyNewsService {
    private final MyNewsRepository myNewsRepository;
    private final AuthFunction authFunction;

    @Transactional
    public ApiResult addMyMyNews(SearchNewspaper searchNewspaper) {
        Member member = authFunction.getOauthId();
        Optional<MyNews> optionalMyNews = myNewsRepository.findByMemberAndSearchNewsPaper(member, searchNewspaper);
        if (optionalMyNews.isPresent()) {
            throw new BusinessException(ALREADY_SAVED_MY_NEWS);
        }

        MyNews savedMyNews = myNewsRepository.save(MyNews.toEntity(member, searchNewspaper));
        member.addMyNews(savedMyNews);

        return success(true, SAVED_MY_NEWS, SUCCESS_CODE);
    }

    public ApiResult<?> myNewsList() {
        Member member = authFunction.getOauthId();
        List<MyNewsRespDto> result = myNewsRepository.myNewsList(member);
        return success(result, SUCCESS, SUCCESS_CODE);
    }

    @Transactional
    public ApiResult<?> myNewsDelete(Long idx) {
        myNewsRepository.deleteById(idx);
        return success(true, SUCCESS, SUCCESS_CODE);
    }
}
