package com.collecting.collecting_data_news.selenium.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DataCollectedDto {
    private String newspaper;
    private String title;
    private String href;
    private String keyword;

    public DataCollectedDto(String newspaper, String title, String href, String keyword) {
        this.newspaper = newspaper;
        this.title = title;
        this.href = href;
        this.keyword = keyword;
    }
}
