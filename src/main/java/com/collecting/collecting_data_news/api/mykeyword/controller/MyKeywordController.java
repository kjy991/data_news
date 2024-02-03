package com.collecting.collecting_data_news.api.mykeyword.controller;

import com.collecting.collecting_data_news.api.mykeyword.service.MyKeywordService;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/keyword")
@RequiredArgsConstructor
@RestController
public class MyKeywordController {
    private final MyKeywordService keywordService;

    //    @PostMapping
    @GetMapping
    public ApiResult<?> addKeyword(@RequestParam String keyword) {
        return keywordService.addMyKeyword(keyword);
    }

    @GetMapping
    public ApiResult<?> myKeywordList() {
        return keywordService.myKeywordList();
    }
}

