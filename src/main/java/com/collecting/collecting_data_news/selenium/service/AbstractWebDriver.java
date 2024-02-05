package com.collecting.collecting_data_news.selenium.service;

import java.util.List;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractWebDriver implements WebDriverOperations {
    protected WebDriver driver;
    @Value("${chrome.driverPath}")
    private String CHROME_DRIVER_PATH;

    @Override
    public List<DataCollectedDto> process(List<KeywordDto> keywords) {
        try {
            setupWebDriver();
            return Optional.ofNullable(keywords)
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream()
                            .flatMap(keyword -> getDataList(keyword).stream())
                            .collect(Collectors.toList())
                    )
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeWebDriver();
        }
        return null;
    }

    @Override
    public synchronized List<DataCollectedDto> getDataList(KeywordDto keyword) {
        return null;
    }

    @Override
    public void setupWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }
    }

    @Override
    public void closeWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Override
    public List<WebElement> findElementsByCssSelector(String cssSelector) {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
    }

    // 하위 클래스에서 구현해야 할 추상 메서드
    protected abstract String getNewsUrl();
}