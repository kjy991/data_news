package com.collecting.collecting_data_news.apiresult.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private final String message;
    private final int status;

    public Result(String message, int status) {
        this.message = message;
        this.status = status;
    }
}