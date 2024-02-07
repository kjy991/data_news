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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    @Scheduled(cron = "9 0 * * * ?")
    public void collectNewsData() {

        System.out.println("naverAbstractWebDriver = " + naverAbstractWebDriver);



        List<KeywordDto> naverKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.NAVER);
        CompletableFuture<List<DataCollectedDto>> naverFuture = CompletableFuture.supplyAsync(() ->
                naverAbstractWebDriver.naverProcess(naverKeywordList));

        List<KeywordDto> daumKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.DAUM);
        CompletableFuture<List<DataCollectedDto>> daumFuture = CompletableFuture.supplyAsync(() ->
                daumAbstractWebDriver.daumProcess(daumKeywordList));

        List<KeywordDto> googleKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.GOOGLE);
        CompletableFuture<List<DataCollectedDto>> googleFuture = CompletableFuture.supplyAsync(() ->
                googleAbstractWebDriver.googleProcess(googleKeywordList));

        CompletableFuture<List<DataCollectedDto>> allFutures = CompletableFuture.allOf(naverFuture, daumFuture, googleFuture)
                .thenApply(ignored -> {
                    List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();
                    dataCollectedDtoList.addAll(naverFuture.join());
                    dataCollectedDtoList.addAll(daumFuture.join());
                    dataCollectedDtoList.addAll(googleFuture.join());
                    return dataCollectedDtoList;
                });

        List<DataCollectedDto> result = allFutures.join();

        if (!result.isEmpty()) {
            newsDataJdbcRepository.batchInsertNewsData(result);
        }
    }
}
