package com.collecting.collecting_data_news.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/home")
@RestController
public class HomeController {

    @GetMapping
    public void user() {


    }
//
//    @GetMapping("/api/oidc") // 요청시 scope 에 openid 가 포함되어야 oidcUser 가 생성된다
//    public Authentication oidc(Authentication authentication, @AuthenticationPrincipal OidcUser oidcUser) {
//        System.out.println("authentication = " + authentication + ", oidcUser = " + oidcUser);
//        return authentication;
//    }
}
