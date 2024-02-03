package com.collecting.collecting_data_news.security.model;


import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String oauthId();

    String getPassword();

    String getEmail();

    String getProvider();

    List<GrantedAuthority> getAuthorities();

    Map<String, Object> getAttributes();


}
