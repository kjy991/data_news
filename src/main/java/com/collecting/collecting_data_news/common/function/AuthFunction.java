package com.collecting.collecting_data_news.common.function;

import com.collecting.collecting_data_news.api.member.repository.MemberRepository;
import com.collecting.collecting_data_news.common.exception.AuthFailException;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.collecting.collecting_data_news.common.apiresult.comcode.ComCode.*;

@RequiredArgsConstructor
@Component
public class AuthFunction {
    private final MemberRepository memberRepository;

    public Member getOauthId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = auth.getName();
        return memberRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new AuthFailException(AUTH_FAIL, AUTH_FAIL_CODE));
    }
}
