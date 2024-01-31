package com.collecting.collecting_data_news.security.service;

import com.tripcoach.app_api.api.member.repository.MemberRepository;
import com.tripcoach.core.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service("userDetailService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String idx) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(idx))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idx: " + idx));

        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getGrade().label()));

        return new MemberContext(member, roles);
    }

}
