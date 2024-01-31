package com.collecting.collecting_data_news.security.handler;


import com.collecting.collecting_data_news.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.security.provider.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.FORBIDDEN_CODE;
import static com.collecting.collecting_data_news.apiresult.response.ApiResponse.error;
import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_FORBIDDEN;


@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = jwtTokenProvider.getClaims(request);


        ApiResult<?> error = error(null, message, FORBIDDEN_CODE);


        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(error);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(SC_FORBIDDEN);
        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }


}