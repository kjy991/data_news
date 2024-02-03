package com.collecting.collecting_data_news.common.apiresult.response;


import com.collecting.collecting_data_news.common.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.common.apiresult.dto.Result;

public class ApiResponse<T> {
    public static <T> ApiResult<T> success(T data, String message, int status) {
        return new ApiResult<>(true, new Result(message, status), data);
    }


    public static <T> ApiResult<?> error(T data, String message, int status) {
        return new ApiResult<>(false, new Result(message, status), data);
    }
}