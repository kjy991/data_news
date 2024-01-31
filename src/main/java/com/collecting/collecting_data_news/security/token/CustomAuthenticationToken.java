package com.collecting.collecting_data_news.security.token;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final String snsId;
    private final String snsType;

    public CustomAuthenticationToken(String snsId, String snsType) {
        super(null);
        this.snsId = snsId;
        this.snsType = snsType;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return snsId;
    }


}
