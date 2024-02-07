package com.collecting.collecting_data_news.api.mykeyword.service;

import com.collecting.collecting_data_news.api.keyword.service.KeywordService;
import com.collecting.collecting_data_news.domain.mykeyword.dto.MyKeywordRespDto;
import com.collecting.collecting_data_news.api.mykeyword.repository.MyKeywordRepository;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.collecting.collecting_data_news.common.function.AuthFunction;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mykeyword.entity.MyKeyword;
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
public class MyKeywordService {
    private final MyKeywordRepository myKeywordRepository;
    private final KeywordService keywordService;
    private final AuthFunction authFunction;

    @Transactional
    public ApiResult addMyKeyword(String strKeyword) {
        Keyword keyword = keywordService.addKeyword(strKeyword);

        Member member = authFunction.getMember();
        Optional<MyKeyword> optionalMyKeyword = myKeywordRepository.findByMemberAndKeyword(member, keyword);
        if (optionalMyKeyword.isPresent()) {
            throw new BusinessException(ALREADY_SAVED_KEYWORD);
        }

        MyKeyword savedMyKeyword = myKeywordRepository.save(MyKeyword.toEntity(member, keyword));
        member.addMyKeywords(savedMyKeyword);

        return success(true, SAVED_KEYWORD, SUCCESS_CODE);
    }

    public ApiResult<?> myKeywordList() {
        Member member = authFunction.getMember();
        List<MyKeywordRespDto> result = myKeywordRepository.myKeywordList(member);
        return success(result, SUCCESS, SUCCESS_CODE);
    }

    @Transactional
    public ApiResult<?> myKeywordDelete(Long idx) {
        myKeywordRepository.deleteById(idx);
        return success(true, SUCCESS, SUCCESS_CODE);
    }
}
