package com.collecting.collecting_data_news.exception;


public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage(); // 예외 메시지만 반환하도록 오버라이드
    }
}