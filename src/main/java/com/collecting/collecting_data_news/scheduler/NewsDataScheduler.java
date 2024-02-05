package com.collecting.collecting_data_news.scheduler;

import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import com.collecting.collecting_data_news.selenium.service.DaumAbstractWebDriver;
import com.collecting.collecting_data_news.selenium.service.GoogleAbstractWebDriver;
import com.collecting.collecting_data_news.selenium.service.NaverAbstractWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsDataScheduler {
    private final NaverAbstractWebDriver naverAbstractWebDriver;
    private final DaumAbstractWebDriver daumAbstractWebDriver;
    private final GoogleAbstractWebDriver googleAbstractWebDriver;
    private final KeywordRepository keywordRepository;

    private final NewsDataJdbcRepository newsDataJdbcRepository;

    /**
     * 0 분
     * 0 시
     * 4 일 (하루 중 4시에 실행)
     * * 월 (모든 월)
     * * 요일 (매일)
     * ? 연도 (연도는 지정되지 않음)
     * 새벽4시에 유저 수락율 매일 업데이트
     */
//    @Scheduled(cron = "0 0 * * * ?")
    public void collectNewsData() {

//        List<KeywordDto> naverKeyword = keywordRepository.keywordDtoList(SearchNewspaper.NAVER);
//        List<KeywordDto> daumKeyword = keywordRepository.keywordDtoList(SearchNewspaper.DAUM);
        List<KeywordDto> googleKeyword = keywordRepository.keywordDtoList(SearchNewspaper.GOOGLE);

//        CompletableFuture<List<DataCollectedDto>> naverFuture = CompletableFuture.supplyAsync(() ->
//                naverAbstractWebDriver.naverProcess(naverKeyword));
//
//        CompletableFuture<List<DataCollectedDto>> daumFuture = CompletableFuture.supplyAsync(() ->
//                daumAbstractWebDriver.daumProcess(daumKeyword));

        CompletableFuture<List<DataCollectedDto>> googleFuture = CompletableFuture.supplyAsync(() ->
                googleAbstractWebDriver.googleProcess(googleKeyword));

//        List<DataCollectedDto> result = CompletableFuture.allOf(naverFuture, daumFuture, googleFuture)
        List<DataCollectedDto> result = CompletableFuture.allOf(googleFuture)
                .thenApply(ignored -> {
                    List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();
//                    dataCollectedDtoList.addAll(naverFuture.join());
//                    dataCollectedDtoList.addAll(daumFuture.join());
                    dataCollectedDtoList.addAll(googleFuture.join());
                    return dataCollectedDtoList;
                }).join();


//        // 네이버 키워드 처리
//        CompletableFuture<List<DataCollectedDto>> naverFuture = CompletableFuture
//                .supplyAsync(() -> keywordRepository.keywordDtoList(SearchNewspaper.NAVER))
//                .thenComposeAsync(naverKeywordDtoList -> CompletableFuture.supplyAsync(() -> naverAbstractWebDriver.naverProcess(naverKeywordDtoList)));
//        // 다음 키워드 처리
//        CompletableFuture<List<DataCollectedDto>> daumFuture = CompletableFuture
//                .supplyAsync(() -> keywordRepository.keywordDtoList(SearchNewspaper.DAUM))
//                .thenComposeAsync(daumKeywordDtoList -> CompletableFuture.supplyAsync(() -> daumAbstractWebDriver.daumProcess(daumKeywordDtoList)));
//
//        // 구글 키워드 처리
//        CompletableFuture<List<DataCollectedDto>> googleFuture = CompletableFuture
//                .supplyAsync(() -> keywordRepository.keywordDtoList(SearchNewspaper.GOOGLE))
//                .thenComposeAsync(googleKeywordDtoList -> CompletableFuture.supplyAsync(() -> googleAbstractWebDriver.googleProcess(googleKeywordDtoList)));
//
//        // 모든 작업을 병렬로 실행하고 결과를 합침
//        CompletableFuture<Void> combinedFuture = CompletableFuture
//                .allOf(naverFuture, daumFuture, googleFuture)
//                .thenRun(() -> {
//                    // 네이버, 다음, 구글 결과를 한 번에 처리
//                    naverFuture.join().addAll(dataCollectedDtoList);
//                    daumFuture.join().addAll(dataCollectedDtoList);
//                    googleFuture.join().addAll(dataCollectedDtoList);
//                });
//
//        // 모든 작업이 완료될 때까지 대기
//        combinedFuture.join();
//
        System.out.println("dataCollectedDtoList.size() = " + result.size());

        if (!result.isEmpty()) {
            newsDataJdbcRepository.batchInsertNewsData(result);
        }
    }
}
