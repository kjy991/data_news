package com.collecting.collecting_data_news.api.news.service;

import com.collecting.collecting_data_news.api.mynews.repository.MyNewsRepository;
import com.collecting.collecting_data_news.api.news.repository.querydsl.NewsSupportRepository;
import com.collecting.collecting_data_news.common.function.AuthFunction;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.dto.MyNewsRespDto;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.domain.news.dto.NewsRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NewsService {
    private final AuthFunction authFunction;
    private final NewsSupportRepository newsSupportRepository;
    private final MyNewsRepository myNewsRepository;

    public Map<SearchNewspaper, Map<String, List<NewsRespDto>>> newsList(Pageable pageable) {
        Member member = authFunction.getMember();
        List<NewsRespDto> newsRespDtoList = myNewsRepository.myNewsList(member)
                .stream()
                .flatMap(myNews -> newsSupportRepository.findNewsRespDtoList(member, myNews.getSearchNewspaper(), pageable).getContent().stream())
                .collect(Collectors.toList());

        return newsGroupingSearchNewsPaperAndKeyword(newsRespDtoList);
    }

    private Map<SearchNewspaper, Map<String, List<NewsRespDto>>> newsGroupingSearchNewsPaperAndKeyword(List<NewsRespDto> newsRespDtoList) {
        // 1. 수집 대상 뉴스로 그룹화
        Map<SearchNewspaper, List<NewsRespDto>> newspaperGroups = newsRespDtoList.stream()
                .collect(Collectors.groupingBy(NewsRespDto::getSearchNewspaper));

        // 2. 저장한 키워드로 그룹화
        Map<SearchNewspaper, Map<String, List<NewsRespDto>>> keywordsGroups = newspaperGroups.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(Collectors.groupingBy(NewsRespDto::getKeyword))
                ));
        return keywordsGroups;
    }


    public void newsInfo(SearchNewspaper searchNewspaper, Pageable pageable, Model model) {
        Member member = authFunction.getMember();
        Page<NewsRespDto> newsRespDtoList = newsSupportRepository.findNewsRespDtoList(member, searchNewspaper, pageable);
        Map<SearchNewspaper, Map<String, List<NewsRespDto>>> searchNewspaperMapMap = newsGroupingSearchNewsPaperAndKeyword(newsRespDtoList.getContent());

        model.addAttribute("news", searchNewspaperMapMap);
        model.addAttribute("newsPageable", newsRespDtoList);
        model.addAttribute("maxPage", 10);
    }
}
