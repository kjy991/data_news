package com.collecting.collecting_data_news.api.mynews.controller;

import com.collecting.collecting_data_news.api.mynews.service.MyNewsService;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/my-news")
@RequiredArgsConstructor
@Controller
public class MyNewsController {
    private final MyNewsService myNewsService;
    @ResponseBody
    @PostMapping
    public ApiResult<?> addMyNews(@RequestBody Map<String, SearchNewspaper> reqBody) {
        return myNewsService.addMyMyNews(reqBody.get("searchNewspaper"));
    }

    @GetMapping
    public String myNewsList(Model model) {
        model.addAttribute("myNews", myNewsService.myNewsList());
        return "view/mynews/list";
    }

    @ResponseBody
    @DeleteMapping
    public ApiResult<?> myMyNewsDelete(@RequestBody Map<String, SearchNewspaper> reqBody) {
        return myNewsService.myNewsDelete(reqBody.get("searchNewspaper"));
    }
}

