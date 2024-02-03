package com.collecting.collecting_data_news.api.keyword.service;

import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.api.mykeyword.repository.MyKeywordRepository;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.collecting.collecting_data_news.common.function.AuthFunction;
import com.collecting.collecting_data_news.domain.keyword.Keyword;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.member.entity.MyKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.ALREADY_SAVED_KEYWORD;
import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.SUCCESS_CODE;
import static com.collecting.collecting_data_news.common.apiresult.response.ApiResponse.success;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public Keyword addKeyword(String keyword) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByKeyword(keyword);
        Keyword savedKeyword;

        if (optionalKeyword.isEmpty()) {
            savedKeyword = keywordRepository.save(Keyword.toEntity(keyword));
        } else {
            savedKeyword = optionalKeyword.get();
        }
        return savedKeyword;
    }
}
