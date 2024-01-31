package com.collecting.collecting_data_news.security.filter;

import com.collecting.collecting_data_news.security.provider.JwtTokenProvider;
import com.collecting.collecting_data_news.security.service.CustomUserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.AUTHORIZATION_HEADER;
import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.BEARER_PREFIX;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        String username = null;
        String jwtToken = null;

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
                jwtToken = requestTokenHeader.substring(7);
                Claims claims = jwtTokenProvider.getUsernameFromToken(jwtToken, request);
                username = claims.getSubject();
                String nickname = (String) claims.get("nickname");
                log.info("유저 인덱스 = {}  유저 닉네임 = {} ", username, nickname);
            }
        } catch (Exception ignored) {
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 토큰이 유효한 경우.. 수동으로 인증을 설정하도록 Spring Security를 구성
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 컨텍스트에서 인증을 설정한 후, 현재 사용자가 인증되었음을 지정. Spring Security Configurations 성공적으로 통과.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
