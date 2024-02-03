package com.collecting.collecting_data_news.common.apiresult.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Data<T> {

    private String key;
    private T result;

    public Data(String key, T result) {
        this.key = key;
        this.result = result;
    }
}
