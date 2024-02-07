package com.collecting.collecting_data_news.api.mynews.service;

import com.collecting.collecting_data_news.api.mynews.repository.MyNewsRepository;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.collecting.collecting_data_news.common.function.AuthFunction;
import com.collecting.collecting_data_news.domain.member.entity.Member;
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
        Member member = authFunction.getMember();
        Optional<MyNews> optionalMyNews = myNewsRepository.findByMemberAndSearchNewsPaper(member, searchNewspaper);
        if (optionalMyNews.isPresent()) {
            throw new BusinessException(ALREADY_SAVED_MY_NEWS);
        }

        MyNews savedMyNews = myNewsRepository.save(MyNews.toEntity(member, searchNewspaper));
        member.addMyNews(savedMyNews);

        return success(true, SAVED_MY_NEWS, SUCCESS_CODE);
    }

    public List<MyNewsRespDto> myNewsList() {
        Member member = authFunction.getMember();
        return myNewsRepository.myNewsList(member);
    }

    @Transactional
    public ApiResult<?> myNewsDelete(SearchNewspaper searchNewspaper) {
        Member member = authFunction.getMember();
        Optional<MyNews> optionalMyNews = myNewsRepository.findByMemberAndSearchNewsPaper(member, searchNewspaper);
        if (optionalMyNews.isEmpty()) {
            throw new BusinessException(ALREADY_DELETE_DATA);
        }
        myNewsRepository.delete(optionalMyNews.get());
        return success(true, SUCCESS, SUCCESS_CODE);
    }
}
