package com.collecting.collecting_data_news.api.keyword.repository;

import com.collecting.collecting_data_news.domain.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByKeyword(String keyword);
}
