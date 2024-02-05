package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface WebDriverOperations {
    void setupWebDriver();

    void closeWebDriver();

    List<WebElement> findElementsByCssSelector(String cssSelector);
    List<DataCollectedDto> getDataList(KeywordDto keyword);
    List<DataCollectedDto> process(List<KeywordDto> keywords);
}