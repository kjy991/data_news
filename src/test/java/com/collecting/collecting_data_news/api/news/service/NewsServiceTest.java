package com.collecting.collecting_data_news.api.news.service;

import com.collecting.collecting_data_news.api.member.repository.MemberRepository;
import com.collecting.collecting_data_news.api.mynews.repository.MyNewsRepository;
import com.collecting.collecting_data_news.api.news.repository.querydsl.NewsSupportRepository;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
import com.collecting.collecting_data_news.domain.news.dto.NewsRespDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class NewsServiceTest {
    @Autowired
    NewsSupportRepository newsSupportRepository;
    @Autowired
    MyNewsRepository myNewsRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findNewsList() {
        PageRequest pageable = PageRequest.of(0, 30);
        Member member = memberRepository.findById(1L).get();


        List<NewsRespDto> newsRespDtoList = myNewsRepository.myNewsList(member)
                .stream()
                .flatMap(mynews -> newsSupportRepository.findNewsRespDtoList(member, mynews.getSearchNewspaper(), pageable).getContent().stream())
                .collect(Collectors.toList());

        Map<SearchNewspaper, List<NewsRespDto>> newspaperGroups = newsRespDtoList.stream()
                .collect(Collectors.groupingBy(NewsRespDto::getSearchNewspaper));

        // 2. 저장한 키워드로 그룹화
        Map<SearchNewspaper, Map<String, List<NewsRespDto>>> keywordGroups = newspaperGroups.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(Collectors.groupingBy(NewsRespDto::getKeyword))
                ));

        keywordGroups.forEach((newspaper, keywordMap) -> {
            System.out.println("신문사: " + newspaper + " 링크 : " );
            keywordMap.forEach((keyword, newsListForKeyword) -> {
                System.out.println(" - 키워드: " + keyword);
                newsListForKeyword.forEach(news -> System.out.println("   * " + news.getTitle()+ " 링크 : " + news.getHref()  ));
            });
        });
    }


}