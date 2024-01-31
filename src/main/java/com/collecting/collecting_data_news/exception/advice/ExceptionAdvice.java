package com.collecting.collecting_data_news.exception.advice;

import com.collecting.collecting_data_news.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.FAIL_CODE;
import static com.collecting.collecting_data_news.apiresult.response.ApiResponse.error;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    /*
    BussinessException 통합 처리
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult businessException(HttpServletRequest request, Exception e) {

        log.error("\n\n========================================ERROR START========================================");
        log.error("요청 URL : " + request.getMethod() + " " + request.getRequestURI());
        log.error("예외 메시지 : " + e.getMessage());
        log.error("\n==========================================ERROR END==========================================\n");

        return error(null, e.getMessage(), FAIL_CODE);
    }
//    ResponseStatusException
}
