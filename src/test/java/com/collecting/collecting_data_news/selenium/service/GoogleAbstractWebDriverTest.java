package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoogleAbstractWebDriverTest {
    @Autowired
    GoogleAbstractWebDriver googleAbstractWebDriver;
    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void googleNewsData() {
        List<KeywordDto> googleKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.GOOGLE);
        List<DataCollectedDto> process = googleAbstractWebDriver.process(googleKeywordList);
        for (DataCollectedDto dataCollectedDto : process) {
            System.out.println("dataCollectedDto = " + dataCollectedDto);
        }
    }
}