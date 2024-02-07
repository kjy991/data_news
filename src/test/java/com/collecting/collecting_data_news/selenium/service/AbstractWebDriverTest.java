package com.collecting.collecting_data_news.selenium.service;

import com.collecting.collecting_data_news.scheduler.NewsDataJdbcRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper.*;
import static com.collecting.collecting_data_news.selenium.elements.SeleniumFindElements.*;

@SpringBootTest
class AbstractWebDriverTest {
    private static WebDriver driver;
    @Autowired

    NewsDataJdbcRepository newsDataJdbcRepository;
    private String NAVER_URL = "https://search.naver.com/search.naver?ssc=tab.news.all&where=news&sm=tab_jum&query=";
    private String CHROME_DRIVER_PATH = "/Users/yeopking/Programing/spring_study/collecting_data_news/chromedriver-mac-arm64_version_121/chromedriver";

    @Test
    public void process() {
        try {
            setupWebDriver();

            List<String> keywords = Arrays.asList(
                    "정치", "과학", "세계", "백엔드 개발자"
                    , "촬영지", "최근", "최대", "최선", "최신", "한눈", "한동안", "한마디", "한순간", "한숨", "줄무늬", "중간", "중개소", "중고", "중고품"
//                    , "최저", "최종", "최초", "최후", "추가", "추위", "추천", "추천서", "추첨", "추측", "출국", "출근길", "출력", "출생", "출석", "출신", "출입", "출입국", "출퇴근", "출판사"
//                    , "취침", "치료제", "치수", "치즈", "친환경", "침", "침실", "칭찬", "카네이션", "카세트", "카센터", "카페인", "카펫", "칸", "칼국수", "캠퍼스", "캠프"
//                    , "커튼", "커플", "컴퓨터실", "바다코끼리", "코너", "코미디", "코미디언", "콘서트", "콘센트", "콩나물", "퀴즈", "큰길", "큰소리", "큰일", "클래식", "클릭", "타입", "탈", "탈춤", "탑승구"
//                    , "탕", "태도", "태양", "태양열", "택배", "테이프", "텐트", "토끼", "토론", "통계", "통신", "통역", "통일", "통증", "통지서", "통화", "퇴근길", "퇴원", "트럭", "특기", "특별", "특산물"
//                    , "틈", "티켓", "파마", "파일", "파전", "판매", "판매자", "팥빙수", "패션쇼", "패스트", "푸드", "팩스", "팬", "편리", "편의점", "평가", "평균", "평등", "평생", "평소", "평화", "포도주"
//                    , "포인트", "포장지", "폭포", "폴더", "표시", "표정", "표지판", "풀", "풀이", "품질", "풍경", "풍선", "프라이팬", "프로그램", "프린터", "플라스틱", "플러그"
                    , "피서", "피아노", "필수", "필수품", "필요성", "학과", "학력", "학문", "학비", "학습", "학자", "학점", "한국학", "한글날", "한낮", "정치", "과학", "세계", "백엔드 개발자", "자바", "음모론", "외계인", "백만장자", "게임", "부동산", "금융"
            );

//            List<KeywordDto> keywords = Arrays.asList(
//                    new KeywordDto(1L, "코스닥"),
//                    new KeywordDto(5L, "과학"),
//                    new KeywordDto(6L, "안녕"),
//                    new KeywordDto(7L, "나스닥"),
//                    new KeywordDto(8L, "부자")
//            );

//            Executor customExecutor = Executors.newFixedThreadPool(4);
//            List<CompletableFuture<List<DataCollectedDto>>> completableFutures = keywords.stream()
//                    .map(keyword -> CompletableFuture.supplyAsync(() -> getDataList(keyword), customExecutor))
//                    .collect(Collectors.toList());
//
//            List<DataCollectedDto> dataCollectedDtoList = completableFutures.stream()
//                    .map(CompletableFuture::join)
//                    .flatMap(List::stream)
//                    .collect(Collectors.toList());


            List<DataCollectedDto> dataCollectedDtoList = keywords.stream()
                    .flatMap(keyword -> getDataList(keyword).stream())
                    .collect(Collectors.toList());

            newsDataJdbcRepository.batchInsertNewsData(dataCollectedDtoList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeWebDriver();
        }
    }

    private List<DataCollectedDto> getDataList(String keyword) {
        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();

        driver.get(NAVER_URL + keyword);
//        driver.get(NAVER_URL + keyword.getWord());

        List<WebElement> newsElements = findElementsByCssSelector(NAVER_NEWS_ELEMENTS_LIST);

        // 여기서 병렬 처리를 고려할 수 있습니다.
        newsElements.parallelStream().forEach(newsElement -> {
            DataCollectedDto dataCollectedDto = getDataCollectedDto(newsElement, NAVER_NEWS_TOP_ELEMENTS, NAVER_NEWS_TOP_NAME, keyword);
            dataCollectedDtoList.add(dataCollectedDto);

            List<WebElement> subBxList = newsElement.findElements(By.cssSelector(NAVER_NEWS_SUB_ELEMENTS_LIST));
            subBxList.parallelStream().forEach(subBx -> {
                DataCollectedDto subDataCollectedDto = getDataCollectedDto(subBx, NAVER_NEWS_SUB_ELEMENTS, NAVER_NEWS_SUB_NAME, keyword);
                dataCollectedDtoList.add(subDataCollectedDto);
            });
        });
        return dataCollectedDtoList;
    }

    private synchronized DataCollectedDto getDataCollectedDto(WebElement subBx, String newsSubElements, String newsSubName, String keyword) {

        WebElement subLink = subBx.findElement(By.cssSelector(newsSubElements));
        String subNewsPaper = replaceNewsPaper(subBx, newsSubName, "언론사 선정");
        String subTitle = subLink.getText();
        String subHref = subLink.getAttribute(HREF);

        return new DataCollectedDto(subNewsPaper, subTitle, subHref, keyword, NAVER);
    }

    private String replaceNewsPaper(WebElement element, String cssSelector, String replaceText) {
        WebElement targetElement = element.findElement(By.cssSelector(cssSelector));
        return targetElement.getText().replace(replaceText, "");
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