package com.collecting.collecting_data_news.security.dto;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import lombok.Data;

@Data
public class LoginRespDto {

    private Long idx;
    private String token;

    public LoginRespDto(Member member, String token) {
        this.idx = member.getId();
        this.token = token;

    }
}
