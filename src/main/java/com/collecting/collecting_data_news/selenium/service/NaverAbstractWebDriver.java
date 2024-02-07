package com.collecting.collecting_data_news.selenium.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Controller;

import static com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper.*;
import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@Slf4j
@Primary
@Component
public class NaverAbstractWebDriver extends AbstractWebDriver implements WebDriverOperations {
    @Value("${chrome.naverUrl}")
    private String NAVER_URL;

    @Override
    protected String getNewsUrl() {
        return NAVER_URL;
    }


    public synchronized List<DataCollectedDto> naverProcess(List<KeywordDto> keywords) {
        return process(keywords);
    }

    @Override
    public synchronized List<DataCollectedDto> getDataList(KeywordDto keyword) {
        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();
        driver.get(NAVER_URL + keyword.getWord());

        List<WebElement> newsElements = findElementsByCssSelector(NAVER_NEWS_ELEMENTS_LIST);

        // 여기서 병렬 처리를 고려할 수 있습니다.
        newsElements.parallelStream().forEach(newsElement -> {
            DataCollectedDto dataCollectedDto = getDataCollectedDto(newsElement, NAVER_NEWS_TOP_ELEMENTS, NAVER_NEWS_TOP_NAME, keyword);
            dataCollectedDtoList.add(dataCollectedDto);

            List<WebElement> subBxList = newsElement.findElements(By.cssSelector(NAVER_NEWS_SUB_ELEMENTS_LIST));
            subBxList.stream().forEach(subBx -> {
                DataCollectedDto subDataCollectedDto = getDataCollectedDto(subBx, NAVER_NEWS_SUB_ELEMENTS, NAVER_NEWS_SUB_NAME, keyword);
                dataCollectedDtoList.add(subDataCollectedDto);
            });
        });
        return dataCollectedDtoList;
    }

    private DataCollectedDto getDataCollectedDto(WebElement subBx, String newsSubElements, String newsSubName, KeywordDto keyword) {
        WebElement subElement = subBx.findElement(By.cssSelector(newsSubElements));
        String newspaper = replaceNewsPaper(subBx, newsSubName, "언론사 선정");
        String title = subElement.getText();
        String href = subElement.getAttribute(HREF);

        return new DataCollectedDto(newspaper, title, href, keyword, NAVER);
    }

    private String replaceNewsPaper(WebElement element, String cssSelector, String replaceText) {
        WebElement targetElement = element.findElement(By.cssSelector(cssSelector));
        return targetElement.getText().replace(replaceText, "");
    }

}