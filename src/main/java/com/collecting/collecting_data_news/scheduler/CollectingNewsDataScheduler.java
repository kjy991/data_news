package com.collecting.collecting_data_news.scheduler;

import com.collecting.collecting_data_news.domain.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import com.collecting.collecting_data_news.selenium.service.DaumWebDriverUtil;
import com.collecting.collecting_data_news.selenium.service.NaverWebDriverUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Component
public class CollectingNewsDataScheduler {

    private final KeywordRepository keywordRepository;
    private final NaverWebDriverUtil naverWebDriverUtil;
    private final DaumWebDriverUtil daumWebDriverUtil;

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
