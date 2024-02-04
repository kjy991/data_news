package com.collecting.collecting_data_news.security.config;

//import com.collecting.collecting_data_news.security.CustomAuthorityMapper;

import com.collecting.collecting_data_news.security.CustomAuthorityMapper;
import com.collecting.collecting_data_news.security.service.CustomOAuth2UserService;
import com.collecting.collecting_data_news.security.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        return
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(withDefaults())
                        .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/static/js/**", "/static/images/**", "/static/css/**", "/static/scss/**").permitAll()
                                .requestMatchers("/api/user").hasAnyRole("SCOPE_profile", "SCOPE_email")
                                .requestMatchers("/api/oidc").hasAnyRole("SCOPE_openid")
                                .requestMatchers("/")
                                .permitAll()
                                .anyRequest().authenticated())
                        .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                                                userInfoEndpointConfig -> userInfoEndpointConfig
                                                        .userService(customOAuth2UserService)
                                                        .oidcUserService(customOidcUserService)

                                        )
                                        .loginPage("/")
                                        .defaultSuccessUrl("/")
                        )
                        .build();
    }


    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }
}


