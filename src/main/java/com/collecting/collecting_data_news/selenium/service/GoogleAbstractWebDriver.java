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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper.GOOGLE;
import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@Slf4j
@Primary
@Component
public class GoogleAbstractWebDriver extends AbstractWebDriver implements WebDriverOperations {
    @Value("${chrome.googleUrl}")
    private String GOOGLE_URL;

    public List<DataCollectedDto> googleProcess(List<KeywordDto> keywords) {
        return process(keywords);
    }

    @Override
    protected String getNewsUrl() {
        return GOOGLE_URL;
    }

    @Override
    public synchronized List<DataCollectedDto> getDataList(KeywordDto keyword) {
        driver.get(GOOGLE_URL + keyword.getWord());
        List<WebElement> newsElements = findElementsByCssSelector(GOOGLE_NEWS_ELEMENTS_LIST);

        return newsElements.parallelStream()
                .map(newsElement -> {
                    String newspaper = newsElement.findElement(By.className(GOOGLE_NEWS_NEWSPAPER)).getText();
                    WebElement titleElement = newsElement.findElement(By.className(GOOGLE_NEWS_CONTENT));
                    String title = titleElement.getText();
                    String href = titleElement.getAttribute(HREF);

                    return new DataCollectedDto(newspaper, title, href, keyword, GOOGLE);
                })
                .toList();
    }
}