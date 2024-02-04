package com.collecting.collecting_data_news.api.mynews.controller;

import com.collecting.collecting_data_news.api.mykeyword.service.MyKeywordService;
import com.collecting.collecting_data_news.api.mynews.service.MyNewsService;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.scheduler.CollectingNewsDataScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/my-news")
@RequiredArgsConstructor
@RestController
public class MyNewsController {
    private final MyNewsService myNewsService;

    //    @PostMapping
    @GetMapping
    public ApiResult<?> addMyNews(@RequestParam SearchNewspaper myNews) {
        return myNewsService.addMyMyNews(myNews);
    }

    @GetMapping("/list")
    public ApiResult<?> myNewsList() {
        return myNewsService.myNewsList();
    }

    @GetMapping("/remove")
    public ApiResult<?> myMyNewsDelete(@RequestParam Long idx) {
        return myNewsService.myNewsDelete(idx);
    }
}
