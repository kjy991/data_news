//package com.collecting.collecting_data_news.selenium;
//
//import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ForkJoinPool;
//import java.util.stream.Collectors;
//
//import static com.collecting.collecting_data_news.selenium.elements.HtmlElements.HREF;
//import static com.collecting.collecting_data_news.selenium.elements.NaverFindElements.*;
//import static com.collecting.collecting_data_news.selenium.elements.NaverFindElements.NEWS_SUB_NAME;
//import static org.junit.jupiter.api.Assertions.*;
//
//class WebDriverUtilTest {
//    private static WebDriver driver;
//
//    private String NAVER_URL = "https://search.naver.com/search.naver?ssc=tab.news.all&where=news&sm=tab_jum&query=";
//    private String CHROME_DRIVER_PATH = "/Users/yeopking/Programing/spring_study/collecting_data_news/chromedriver-mac-arm64_version_121/chromedriver";
//
//    @Test
//    public void test() {
//        process();
//    }
//
//    public void process() {
//        try {
//            setupWebDriver();
//            List<String> keywords = Arrays.asList(
//                    "정치", "과학", "세계", "백엔드 개발자"
//                    , "촬영지", "최근", "최대", "최선", "최신", "한눈", "한동안", "한마디", "한순간", "한숨", "줄무늬", "중간", "중개소", "중고", "중고품"
//                    , "최저", "최종", "최초", "최후", "추가", "추위", "추천", "추천서", "추첨", "추측", "출국", "출근길", "출력", "출생", "출석", "출신", "출입", "출입국", "출퇴근", "출판사"
//                    , "취침", "치료제", "치수", "치즈", "친환경", "침", "침실", "칭찬", "카네이션", "카세트", "카센터", "카페인", "카펫", "칸", "칼국수", "캠퍼스", "캠프"
//                    , "커튼", "커플", "컴퓨터실", "바다코끼리", "코너", "코미디", "코미디언", "콘서트", "콘센트", "콩나물", "퀴즈", "큰길", "큰소리", "큰일", "클래식", "클릭", "타입", "탈", "탈춤", "탑승구"
//                    , "탕", "태도", "태양", "태양열", "택배", "테이프", "텐트", "토끼", "토론", "통계", "통신", "통역", "통일", "통증", "통지서", "통화", "퇴근길", "퇴원", "트럭", "특기", "특별", "특산물"
//                    , "틈", "티켓", "파마", "파일", "파전", "판매", "판매자", "팥빙수", "패션쇼", "패스트", "푸드", "팩스", "팬", "편리", "편의점", "평가", "평균", "평등", "평생", "평소", "평화", "포도주"
//                    , "포인트", "포장지", "폭포", "폴더", "표시", "표정", "표지판", "풀", "풀이", "품질", "풍경", "풍선", "프라이팬", "프로그램", "프린터", "플라스틱", "플러그"
//                    , "피서", "피아노", "필수", "필수품", "필요성", "학과", "학력", "학문", "학비", "학습", "학자", "학점", "한국학", "한글날", "한낮", "정치", "과학", "세계", "백엔드 개발자", "자바", "음모론", "외계인", "백만장자", "게임", "부동산", "금융"
//                    , "촬영지", "최근", "최대", "최선", "최신", "한눈", "한동안", "한마디", "한순간", "한숨", "줄무늬", "중간", "중개소", "중고", "중고품"
//            );
//
//            ForkJoinPool customThreadPool = new ForkJoinPool(20);
//            List<DataCollectedDto> dataCollectedDtoList = customThreadPool.submit(() ->
//                    keywords.parallelStream()
//                            .flatMap(keyword -> getDataList(keyword).stream())
//                            .collect(Collectors.toList())
//            ).join();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeWebDriver();
//
//        }
//
//
//    }
//
//    private void setupWebDriver() {
//        if (driver == null) {
//            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
//            driver = new ChromeDriver(options);
//        }
//    }
//
//    private void closeWebDriver() {
//        if (driver != null) {
//            driver.quit();
//            driver = null; // 드라이버를 종료한 후에 null로 초기화
//        }
//    }
//
//    private List<WebElement> findElementsByCssSelector(String cssSelector) {
//        return new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
//    }
//
//    private List<DataCollectedDto> getDataList(String keyword) {
//        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();
//        driver.get(NAVER_URL + keyword);
//
//        List<WebElement> newsElements = findElementsByCssSelector(NEWS_ELEMENTS_LIST);
//
//        // 여기서 병렬 처리를 고려할 수 있습니다.
//        newsElements.parallelStream().forEach(newsElement -> {
//            DataCollectedDto dataCollectedDto = getDataCollectedDtoList(newsElement, NEWS_TOP_ELEMENTS, NEWS_TOP_NAME, keyword);
//            dataCollectedDtoList.add(dataCollectedDto);
//
//            List<WebElement> subBxList = newsElement.findElements(By.cssSelector(NEWS_SUB_ELEMENTS_LIST));
//            subBxList.parallelStream().forEach(subBx -> {
//                DataCollectedDto subDataCollectedDto = getDataCollectedDtoList(subBx, NEWS_SUB_ELEMENTS, NEWS_SUB_NAME, keyword);
//                dataCollectedDtoList.add(subDataCollectedDto);
//            });
//        });
//        return dataCollectedDtoList;
//    }
//
//
//    private DataCollectedDto getDataCollectedDtoList(WebElement subBx, String newsSubElements, String newsSubName, String keyword) {
//        WebElement subLink = subBx.findElement(By.cssSelector(newsSubElements));
//        String subNewsPaper = replaceNewsPaper(subBx, newsSubName, "언론사 선정");
//        String subTitle = subLink.getText();
//        String subHref = subLink.getAttribute(HREF);
//        return new DataCollectedDto(subNewsPaper, subTitle, subHref, keyword);
//    }
//
//
//    private String replaceNewsPaper(WebElement element, String cssSelector, String replaceText) {
//        WebElement targetElement = element.findElement(By.cssSelector(cssSelector));
//        return targetElement.getText().replace(replaceText, "");
//    }
//
//}