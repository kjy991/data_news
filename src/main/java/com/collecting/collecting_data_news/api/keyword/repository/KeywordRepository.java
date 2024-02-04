package com.collecting.collecting_data_news.api.keyword.repository;

import com.collecting.collecting_data_news.api.keyword.repository.querydsl.KeywordCustom;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long>, KeywordCustom {
    Optional<Keyword> findByWord(String keyword);
}
