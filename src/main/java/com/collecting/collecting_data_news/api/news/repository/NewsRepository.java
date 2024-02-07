package com.collecting.collecting_data_news.api.news.repository;


import com.collecting.collecting_data_news.domain.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
