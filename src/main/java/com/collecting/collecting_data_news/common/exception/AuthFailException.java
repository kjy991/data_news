package com.collecting.collecting_data_news.common.exception;


import lombok.Getter;

@Getter
public class AuthFailException extends RuntimeException {
    private final Object data;

    public AuthFailException(String message, Object data) {
        super(message);
        this.data = data;
    }

    @Override
    public String toString() {
        return getMessage(); // 예외 메시지만 반환하도록 오버라이드
    }
}