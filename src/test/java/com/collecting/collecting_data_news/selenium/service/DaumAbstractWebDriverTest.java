package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@SpringBootTest
class DaumAbstractWebDriverTest {
    private static WebDriver driver;

    private String DAUM_URL = "https://search.daum.net/search?w=news&nil_search=btn&DA=NTB&enc=utf8&cluster=y&cluster_page=1&q=";
    private String CHROME_DRIVER_PATH = "/Users/yeopking/Programing/spring_study/collecting_data_news/chromedriver-mac-arm64_version_121/chromedriver";

    @Autowired
    DaumAbstractWebDriver daumAbstractWebDriver;

    @Test
    public void test() {
        List<KeywordDto> keywords = Arrays.asList(
                new KeywordDto(1L, "코스닥"),
                new KeywordDto(5L, "과학"),
                new KeywordDto(6L, "안녕"),
                new KeywordDto(7L, "나스닥"),
                new KeywordDto(8L, "부자")
        );

        List<DataCollectedDto> dataCollectedDtoList = daumAbstractWebDriver.daumProcess(keywords);
        for (DataCollectedDto dataCollectedDto : dataCollectedDtoList) {
            System.out.println("dataCollectedDto = " + dataCollectedDto);
        }
    }

    @Test
    public void process() {
        try {
            setupWebDriver();

            List<KeywordDto> keywords = Arrays.asList(
                    new KeywordDto(1L, "코스닥"),
                    new KeywordDto(5L, "과학"),
                    new KeywordDto(6L, "안녕"),
                    new KeywordDto(7L, "나스닥"),
                    new KeywordDto(8L, "부자")
            );

//            Executor customExecutor = Executors.newFixedThreadPool(4);
//            List<CompletableFuture<List<DataCollectedDto>>> completableFutures = keywords.stream()
//                    .map(keyword -> CompletableFuture.supplyAsync(() -> getDataList(keyword), customExecutor))
//                    .collect(Collectors.toList());
//            List<DataCollectedDto> dataCollectedDtoList = completableFutures.stream()
//                    .map(CompletableFuture::join)
//                    .flatMap(List::stream)
//                    .collect(Collectors.toList());

//            ForkJoinPool customThreadPool = new ForkJoinPool(50);
//            List<DataCollectedDto> dataCollectedDtoList = customThreadPool.submit(() ->
//                    keywords.parallelStream()
//                            .flatMap(keyword -> getDataList(keyword).stream())
//                            .collect(Collectors.toList())
//            ).join();
//            List<DataCollectedDto> dataCollectedDtoList = keywords.stream()
//                    .flatMap(keyword -> getDataList(keyword).stream())
//                    .collect(Collectors.toList());

            getDataList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeWebDriver();

        }


    }

    private synchronized void getDataList() {
        driver.get(DAUM_URL + "과학");

        // 모든 리스트 아이템 가져오기
        List<WebElement> listItems = findElementsByCssSelector(DAUM_NEWS_ELEMENTS_LIST);

        // 각 리스트 아이템에 대해 데이터 추출
        for (WebElement listItem : listItems) {
            // 신문사 추출
            String newspaper = listItem.findElement(By.cssSelector(DAUM_NEWS_NEWSPAPER)).getText();

            // 링크 추출
            String link = listItem.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getAttribute(HREF);

            // 타이틀 추출
            String title = listItem.findElement(By.cssSelector(DAUM_NEWS_CONTENT)).getText();

            // 추출한 데이터 출력
            System.out.println("신문사: " + newspaper);
            System.out.println("링크: " + link);
            System.out.println("타이틀: " + title);
            System.out.println("-------------------------------------");
        }
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