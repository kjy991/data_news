package com.collecting.collecting_data_news.common.apiresult.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResult<T> {
    private final boolean success;
    private final T data;
    private final Result result;

    public ApiResult(boolean success,  Result result,T data) {
        this.success = success;
        this.data = data;
        this.result = result;
    }
}