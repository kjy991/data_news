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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class NewsDataScheduler {
    private final NaverAbstractWebDriver naverAbstractWebDriver;
    private final DaumAbstractWebDriver daumAbstractWebDriver;
    private final GoogleAbstractWebDriver googleAbstractWebDriver;
    private final KeywordRepository keywordRepository;

    private final NewsDataJdbcRepository newsDataJdbcRepository;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void collectNewsData() {
        try {
            System.out.println("collectNewsData 실행");

            List<KeywordDto> googleKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.GOOGLE);
            List<KeywordDto> naverKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.NAVER);
            List<KeywordDto> daumKeywordList = keywordRepository.keywordDtoList(SearchNewspaper.DAUM);


            CompletableFuture<List<DataCollectedDto>> naverFuture = CompletableFuture.supplyAsync(() ->
                    naverAbstractWebDriver.naverProcess(naverKeywordList));

            CompletableFuture<List<DataCollectedDto>> daumFuture = CompletableFuture.supplyAsync(() ->
                    daumAbstractWebDriver.daumProcess(daumKeywordList));

            CompletableFuture<List<DataCollectedDto>> googleFuture = CompletableFuture.supplyAsync(() ->
                    googleAbstractWebDriver.googleProcess(googleKeywordList));

            CompletableFuture<List<DataCollectedDto>> allFutures = CompletableFuture.allOf(naverFuture, daumFuture, googleFuture)
                    .thenApply(ignored -> Stream.of(naverFuture, daumFuture, googleFuture)
                            .map(CompletableFuture::join)
                            .flatMap(List::stream)
                            .collect(Collectors.toList()))
                    .exceptionally(ex -> {
                        log.error("An exception occurred while collecting news data: {}", ex.getMessage());
                        return Collections.emptyList();
                    });
            List<DataCollectedDto> result = allFutures.join();

            if (!result.isEmpty()) {
                newsDataJdbcRepository.batchInsertNewsData(result);
            }
            System.out.println("collectNewsData 종료");
        } catch (Exception e) {
            throw e;
        }

    }
}


//
//for (KeywordDto keywordDto : naverKeywordList) {
//        System.out.println("naverKeywordList = " + keywordDto);
//        }
//        for (KeywordDto keywordDto : daumKeywordList) {
//        System.out.println("daumKeywordList = " + keywordDto);
//        }
//        for (KeywordDto keywordDto : googleKeywordList) {
//        System.out.println("googleKeywordList = " + keywordDto);
//        }
//        List<DataCollectedDto> naverFuture = naverAbstractWebDriver.naverProcess(naverKeywordList);
//        System.out.println("naverFuture.size() = " + naverFuture.size());
//        for (DataCollectedDto dataCollectedDto : naverFuture) {
//        System.out.println("dataCollectedDto = " + dataCollectedDto);
//        }
//        List<DataCollectedDto> daumFuture = daumAbstractWebDriver.daumProcess(daumKeywordList);
//        System.out.println("daumFuture.size() = " + daumFuture.size());
//        for (DataCollectedDto dataCollectedDto : daumFuture) {
//        System.out.println("dataCollectedDto = " + dataCollectedDto);
//        }
//        List<DataCollectedDto> googleFuture = googleAbstractWebDriver.googleProcess(googleKeywordList);
////        System.out.println("googleFuture.size() = " + googleFuture.size());
//        for (DataCollectedDto dataCollectedDto : googleFuture) {
//        System.out.println("dataCollectedDto = " + dataCollectedDto);
//        }
//
//        List<DataCollectedDto> result = new ArrayList<>();
//        if (!naverFuture.isEmpty()) {
//        result.addAll(naverFuture);
//        }
//        if (!daumFuture.isEmpty()) {
//        result.addAll(daumFuture);
//        }
//        if (googleFuture != null) {
//        result.addAll(googleFuture);
//        }