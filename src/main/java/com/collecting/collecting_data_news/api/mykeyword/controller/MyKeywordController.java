package com.collecting.collecting_data_news.api.mykeyword.controller;

import com.collecting.collecting_data_news.api.mykeyword.service.MyKeywordService;
import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.scheduler.NewsDataScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/my-keyword")
@RequiredArgsConstructor
@Controller
public class MyKeywordController {
    private final MyKeywordService keywordService;
    @ResponseBody
    @PostMapping
    public ApiResult<?> addKeyword(@RequestBody Map<String, String > reqBody) {
        return keywordService.addMyKeyword(reqBody.get("keyword"));
    }

    @GetMapping
    public String myKeywordList(Model model) {
        model.addAttribute("myKeywords", keywordService.myKeywordList());
        return "view/mykeyword/list";
    }

    @ResponseBody
    @DeleteMapping
    public ApiResult<?> myKeywordDelete(@RequestBody Map<String, Long> reqBody) {
        return keywordService.myKeywordDelete(reqBody.get("keyword"));

    }
}


