package com.collecting.collecting_data_news.security.exception;

import com.collecting.collecting_data_news.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.security.enums.SecurityCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

import static com.collecting.collecting_data_news.apiresult.response.ApiResponse.error;
import static com.collecting.collecting_data_news.security.enums.SecurityCode.*;


@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        SecurityCode jwtException = (SecurityCode) request.getAttribute(JWT_EXCEPTION.label());


        String errorMessage;
        int errorCode;

        if (jwtException != null) {
            switch (jwtException) {
                case EXPIRED_JWT_EXCEPTION:
                    errorMessage = EXPIRED_JWT_EXCEPTION.message();
                    errorCode = 401;
                    break;
                case MALFORMED_JWT_EXCEPTION:
                    errorMessage = MALFORMED_JWT_EXCEPTION.message();
                    errorCode = 401;
                    break;
                case UNSUPPORTED_JWT_EXCEPTION:
                    errorMessage = UNSUPPORTED_JWT_EXCEPTION.message();
                    errorCode = 401;
                    break;
                case SIGNATURE_EXCEPTION:
                    errorMessage = SIGNATURE_EXCEPTION.message();
                    errorCode = 401;
                    break;
                case ILLEGAL_ARGUMENT_EXCEPTION:
                    errorMessage = ILLEGAL_ARGUMENT_EXCEPTION.message();
                    errorCode = 400;
                    break;
                case NULL_POINT_EXCEPTION:
                    errorMessage = NULL_POINT_EXCEPTION.message();
                    errorCode = 400;
                    break;
                default:
                    errorMessage = JWT_EXCEPTION.message(); // 기본적으로 설정할 메시지
                    errorCode = 500;
                    break;
            }
        } else {
            errorMessage = "익명 사용자 입니다.";
            errorCode = 403;
        }

        ApiResult<?> error = error(jwtException, errorMessage, errorCode);

        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(error);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode);

        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();

    }

}