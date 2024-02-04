package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.api.keyword.dto.KeywordDto;
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
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.collecting.collecting_data_news.domain.news.SearchNewspaper.*;
import static com.collecting.collecting_data_news.selenium.elements.HtmlElements.HREF;
import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@Slf4j
@Component
public class DaumWebDriverUtil {
    private static WebDriver driver;
    @Value("${chrome.daumUrl}")
    private String DAUM_URL;
    @Value("${chrome.driverPath}")
    private String CHROME_DRIVER_PATH;

    public List<DataCollectedDto> process(List<KeywordDto> keywords) {
        try {
            setupWebDriver();
            return keywords.stream()
                    .flatMap(keyword -> getDataList(keyword).stream())
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeWebDriver();
        }
        return null;
    }

    private synchronized List<DataCollectedDto> getDataList(KeywordDto keyword) {
        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();
        driver.get(DAUM_URL + keyword.getWord());

        List<WebElement> newsElements = findElementsByCssSelector(DAUM_NEWS_ELEMENTS_LIST);

        newsElements.stream().forEach(newsElement -> {
            String newspaper = newsElement.findElement(By.cssSelector(DAUM_NEWS_NEWSPAPER)).getText();
            String href = newsElement.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getAttribute(HREF);
            String title = newsElement.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getText();

            dataCollectedDtoList.add(new DataCollectedDto(newspaper, title, href, keyword, DAUM));
        });
        return dataCollectedDtoList;
    }


    private void setupWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }
    }

    private void closeWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // 드라이버를 종료한 후에 null로 초기화
        }
    }

    private List<WebElement> findElementsByCssSelector(String cssSelector) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
    }

}