package com.collecting.collecting_data_news.security.handler;

import com.collecting.collecting_data_news.apiresult.dto.ApiResult;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.security.dto.LoginRespDto;
import com.collecting.collecting_data_news.security.provider.JwtTokenProvider;
import com.collecting.collecting_data_news.security.service.MemberContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.SUCCESS;
import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.SUCCESS_CODE;
import static com.collecting.collecting_data_news.apiresult.response.ApiResponse.success;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        Member member = ((MemberContext) authentication.getPrincipal()).getMember();

        String token = jwtTokenProvider.generateToken(member);
        LoginRespDto result = new LoginRespDto(member, token);

        ApiResult<LoginRespDto> success = success(result, SUCCESS, SUCCESS_CODE);

        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(success);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();

        // 로그인 성공했을때만 이력을 남김
    }
}