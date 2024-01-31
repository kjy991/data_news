package com.collecting.collecting_data_news.security.service;

import com.tripcoach.core.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MemberContext extends User {

    private final Member member;

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(String.valueOf(member.getIdx()), "beeb.tripcoach", authorities);
        this.member = member;
    }
}