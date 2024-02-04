package com.collecting.collecting_data_news.api.mynews.repository;

import com.collecting.collecting_data_news.api.mynews.repository.querydsl.MyNewsCustom;
import com.collecting.collecting_data_news.domain.mynews.entity.MyNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyNewsRepository extends JpaRepository<MyNews, Long>, MyNewsCustom {

}
