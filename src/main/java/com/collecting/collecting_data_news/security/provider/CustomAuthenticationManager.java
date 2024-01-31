package com.collecting.collecting_data_news.security.provider;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.member.enums.OsType;
import com.collecting.collecting_data_news.domain.member.enums.SnsType;
import com.collecting.collecting_data_news.exception.BusinessException;
import com.collecting.collecting_data_news.security.token.CustomAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import static com.collecting.collecting_data_news.apiresult.comcode.ComCode.LOGIN_FAIL;
import static io.jsonwebtoken.lang.Strings.hasText;


public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MemberRepository memberRepository;


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) authentication;

        String snsId = customAuthenticationToken.getSnsId();
        SnsType snsType = SnsType.valueOf(customAuthenticationToken.getSnsType());

        Member member = memberRepository.findBySnsIdAndSnsType(snsId, snsType)
                .orElseThrow(() -> new BadCredentialsException(LOGIN_FAIL));


        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(member.getId()));
        if (userDetails == null) {
            throw new BusinessException(LOGIN_FAIL);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}









