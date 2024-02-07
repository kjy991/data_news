package com.collecting.collecting_data_news.api.keyword.service;

import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.common.exception.BusinessException;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.FIND_FAIL;
import static com.collecting.collecting_data_news.common.function.UtilityFunction.isValidString;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public Keyword addKeyword(String keyword) {
        if (!isValidString(keyword)) {
            throw new BusinessException("특수문자 & 기호는 저장이 안됩니다.");
        }

        Optional<Keyword> optionalKeyword = keywordRepository.findByWord(keyword);
        Keyword savedKeyword;

        if (optionalKeyword.isEmpty()) {
            savedKeyword = keywordRepository.save(Keyword.toEntity(keyword));
        } else {
            savedKeyword = optionalKeyword.get();
        }
        return savedKeyword;
    }
}
