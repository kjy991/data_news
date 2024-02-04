package com.collecting.collecting_data_news.common.apiresult.comcode;

public class ComCode {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";


    public final static String SUCCESS = "성공 했습니다.";
    public final static int SUCCESS_CODE = 200;

    public final static String FIND_FAIL = "요청을 처리하지 못했어요\n관리자에게 문의해주세요!";
    public final static int FAIL_CODE = 400;
    public final static int FORBIDDEN_CODE = 403;
    public final static String FORBIDDEN = "권한이 없습니다.";

    public final static String AUTH_FAIL = "인증에 실패했습니다.";
    public final static int AUTH_FAIL_CODE = 401; // 인증 실패

    public final static String SAVED_KEYWORD = "검색어가 저장 되었습니다.";
    public final static String ALREADY_SAVED_KEYWORD = "이미 저장된 키워드 입니다.";
    public final static String CONVERT_ENUM_FAIL = " -> ENUM 변환 실패 했습니다";


}
