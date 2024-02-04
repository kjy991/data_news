package com.collecting.collecting_data_news.scheduler;

import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import com.collecting.collecting_data_news.selenium.service.DaumWebDriverUtil;
import com.collecting.collecting_data_news.selenium.service.NaverWebDriverUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollectingNewsDataSchedulerTest {
    @Autowired
    KeywordRepository keywordRepository;
    @Autowired
    NaverWebDriverUtil naverWebDriverUtil;
    @Autowired
    DaumWebDriverUtil daumWebDriverUtil;

    @Autowired
    NewsDataJdbcRepository newsDataJdbcRepository;

    @Test
    public void collectNewsData() {
        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();

        // 네이버 키워드 처리
        CompletableFuture<List<DataCollectedDto>> naverFuture = CompletableFuture
                .supplyAsync(() -> keywordRepository.keywordDtoList(SearchNewspaper.NAVER))
                .thenComposeAsync(naverKeywordDtoList -> CompletableFuture.supplyAsync(() -> naverWebDriverUtil.process(naverKeywordDtoList)));

        // 다음 키워드 처리
        CompletableFuture<List<DataCollectedDto>> daumFuture = CompletableFuture
                .supplyAsync(() -> keywordRepository.keywordDtoList(SearchNewspaper.DAUM))
                .thenComposeAsync(daumKeywordDtoList -> CompletableFuture.supplyAsync(() -> daumWebDriverUtil.process(daumKeywordDtoList)));

        // 두 작업을 병렬로 실행하고 결과를 합침
        CompletableFuture<Void> combinedFuture = naverFuture
                .thenCombineAsync(daumFuture, (naverDataCollectedDto, daumDataCollectedDto) -> {
                    dataCollectedDtoList.addAll(naverDataCollectedDto);
                    dataCollectedDtoList.addAll(daumDataCollectedDto);
                    return null;
                });

        // 모든 작업이 완료될 때까지 대기
        combinedFuture.join();

        System.out.println("dataCollectedDtoList.size() = " + dataCollectedDtoList.size());

        if (!dataCollectedDtoList.isEmpty()) {
            newsDataJdbcRepository.batchInsertNewsData(dataCollectedDtoList);
        }
    }
}