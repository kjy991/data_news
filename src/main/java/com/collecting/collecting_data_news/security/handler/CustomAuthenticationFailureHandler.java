package com.collecting.collecting_data_news.security.handler;

import com.collecting.collecting_data_news.apiresult.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.AUTH_FAIL_CODE;
import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.LOGIN_FAIL;


public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ApiResult<?> error = error(null, LOGIN_FAIL, AUTH_FAIL_CODE);

        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(error);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }


}
