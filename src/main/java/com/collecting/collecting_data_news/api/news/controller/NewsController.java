package com.collecting.collecting_data_news.api.news.controller;

import com.collecting.collecting_data_news.api.news.service.NewsService;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.domain.news.dto.NewsRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Native;

import static com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper.*;

@RequiredArgsConstructor
@RequestMapping("/news")
@Controller
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public String newsTotalList(Model model, Pageable pageable) {
        model.addAttribute("totalNews", newsService.newsList(pageable));
        return "view/news/totalNews";
    }

    @GetMapping("/info/{searchNewspaper}")
    public String newsInfo(@PathVariable SearchNewspaper searchNewspaper, Model model, Pageable pageable) {
        newsService.newsInfo(searchNewspaper, pageable, model);
        return "view/news/newsInfo";
    }

    @GetMapping("/info")
    public String newsInfoPage(@RequestParam SearchNewspaper searchNewspaper, Model model, Pageable pageable) {
         newsService.newsInfo(searchNewspaper, pageable, model);
        return "view/news/newsInfo :: #news";
    }
}
