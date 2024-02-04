package com.collecting.collecting_data_news.scheduler;

import com.collecting.collecting_data_news.api.keyword.dto.KeywordDto;
import com.collecting.collecting_data_news.api.keyword.repository.KeywordRepository;
import com.collecting.collecting_data_news.selenium.dto.DataCollectedDto;
import com.collecting.collecting_data_news.selenium.service.DaumWebDriverUtil;
import com.collecting.collecting_data_news.selenium.service.NaverWebDriverUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        List<KeywordDto> keywordDtos = keywordRepository.findAll()
                .stream()
                .map(KeywordDto::new)
                .toList();
        List<DataCollectedDto> dataCollectedDtoList = new ArrayList<>();

        List<DataCollectedDto> process = naverWebDriverUtil.process(keywordDtos);
        List<DataCollectedDto> process1 = daumWebDriverUtil.process(keywordDtos);
        dataCollectedDtoList.addAll(process);
        dataCollectedDtoList.addAll(process1);
        System.out.println("dataCollectedDtoList.size() = " + dataCollectedDtoList.size());

        if (!process.isEmpty()) {
            newsDataJdbcRepository.batchInsertNewsData(dataCollectedDtoList);
        }
    }
}
