
package com.collecting.collecting_data_news.security.service;

import com.collecting.collecting_data_news.api.member.repository.MemberRepository;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.security.model.GoogleUser;
import com.collecting.collecting_data_news.security.model.ProviderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;


public abstract class AbstractOAuth2UserService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest, String registrationId) {
        Optional<Member> member = memberRepository.findByOauthTypeAndOauthId(registrationId, providerUser.oauthId());
        if (member.isEmpty()) {
            registrationId = userRequest.getClientRegistration().getRegistrationId();
            memberService.register(registrationId, providerUser);
        }
    }

    protected ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();
        if (registrationId.equals("google")) {
            return new GoogleUser(oAuth2User, clientRegistration);
        }
        return null;
    }


}
