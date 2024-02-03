
package com.collecting.collecting_data_news.security.service;

import com.collecting.collecting_data_news.api.member.repository.MemberRepository;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.security.model.ProviderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Transactional
    public void register(String oauthType, ProviderUser providerUser) {
        Member member = Member.builder()
                .oauthType(oauthType)
                .oauthId(providerUser.oauthId())
                .nickname((String) providerUser.getAttributes().get("name"))
                .provider(providerUser.getPassword())
                .email(providerUser.getEmail())
                .build();
        memberRepository.save(member);
    }
}

