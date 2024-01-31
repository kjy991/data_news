package com.collecting.collecting_data_news.security.filter;

import com.collecting.collecting_data_news.security.token.CustomAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        String snsId = request.getParameter("snsId");
        String snsType = request.getParameter("snsType");

        CustomAuthenticationToken customToken = new CustomAuthenticationToken(snsId, snsType);

        return getAuthenticationManager().authenticate(customToken);
    }

}


