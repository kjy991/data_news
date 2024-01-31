package com.collecting.collecting_data_news.security.enums;



public enum SecurityCode {
    JWT_EXCEPTION("JWT_EXCEPTION", "JWT TOKEN 만료 되었거나 잘못 되었습니다."),
    EXPIRED_JWT_EXCEPTION("ExpiredJwtException", "토큰이 만료 되었습니다.\n토큰을 재발급 해주세요"),
    MALFORMED_JWT_EXCEPTION("MalformedJwtException", "손상된 토큰입니다.\n관리자에게 문의해주세요"),
    UNSUPPORTED_JWT_EXCEPTION("UnsupportedJwtException", "지원하지 않은 토큰 입니다."),
    SIGNATURE_EXCEPTION("SignatureException", "시그니처 검증에 실패한 토큰 입니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException", "관리자에게 문의해주세요"),
    NULL_POINT_EXCEPTION("NullPointException", "빈값 입니다"),
    ;

    private final String label;
    private final String message;

    SecurityCode(String label, String message) {
        this.label = label;
        this.message = message;
    }

    public String label() {
        return label;
    }

    public String message() {
        return message;
    }

}