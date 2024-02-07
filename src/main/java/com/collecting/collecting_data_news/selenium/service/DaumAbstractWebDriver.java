package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper.*;
import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@Slf4j
@Primary
@Component
public class DaumAbstractWebDriver extends AbstractWebDriver implements WebDriverOperations {
    @Value("${chrome.daumUrl}")
    private String DAUM_URL;

    public synchronized List<DataCollectedDto> daumProcess(List<KeywordDto> keywords) {
        return process(keywords);
    }

    @Override
    protected String getNewsUrl() {
        return DAUM_URL;
    }

    @Override
    public synchronized List<DataCollectedDto> getDataList(KeywordDto keyword) {
        driver.get(DAUM_URL + keyword.getWord());

        List<WebElement> newsElements = findElementsByCssSelector(DAUM_NEWS_ELEMENTS_LIST);
        return newsElements.parallelStream()
                .map(newsElement -> {
                    String newspaper = newsElement.findElement(By.cssSelector(DAUM_NEWS_NEWSPAPER)).getText();
                    String href = newsElement.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getAttribute(HREF);
                    String title = newsElement.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getText();

                    return new DataCollectedDto(newspaper, title, href, keyword, DAUM);
                })
                .toList();
    }


}