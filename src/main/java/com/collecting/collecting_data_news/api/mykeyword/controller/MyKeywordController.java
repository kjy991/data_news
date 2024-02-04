package com.collecting.collecting_data_news.api.mykeyword.controller;

import com.collecting.collecting_data_news.api.mykeyword.service.MyKeywordService;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.scheduler.CollectingNewsDataScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/keyword")
@RequiredArgsConstructor
@RestController
public class MyKeywordController {
    private final MyKeywordService keywordService;
    private final CollectingNewsDataScheduler collectingNewsDataScheduler;

    //    @PostMapping
    @GetMapping
    public ApiResult<?> addKeyword(@RequestParam String keyword) {
        return keywordService.addMyKeyword(keyword);
    }

    @GetMapping("/list")
    public ApiResult<?> myKeywordList() {
        return keywordService.myKeywordList();
    }

    @GetMapping("/remove")
    public ApiResult<?> myKeywordDelete(@RequestParam Long idx) {
        collectingNewsDataScheduler.collectNewsData();
        return keywordService.myKeywordDelete(idx);
    }
}

