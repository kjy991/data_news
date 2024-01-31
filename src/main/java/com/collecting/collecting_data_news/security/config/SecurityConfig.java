package com.collecting.collecting_data_news.security.config;

import com.collecting.collecting_data_news.security.exception.JwtAuthenticationEntryPoint;
import com.collecting.collecting_data_news.security.filter.CustomAuthenticationFilter;
import com.collecting.collecting_data_news.security.filter.JwtAuthenticationFilter;
import com.collecting.collecting_data_news.security.handler.CustomAccessDeniedHandler;
import com.collecting.collecting_data_news.security.handler.CustomAuthenticationFailureHandler;
import com.collecting.collecting_data_news.security.handler.CustomAuthenticationSuccessHandler;
import com.collecting.collecting_data_news.security.provider.CustomAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(getCorsConfigurerCustomizer())
                .authorizeHttpRequests(getAuthorizationManagerRequestMatcherRegistryCustomizer())
                .exceptionHandling(getExceptionHandlingConfigurerCustomizer()) // exception handling 할 때 우리가 만든 클래스를 추가.
                .sessionManagement(getSessionManagementConfigurerCustomizer()) // 시큐리티는 기본적으로 세션을 사용하지만, 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add a filter to validate the tokens with every request
                .build();
    }

    @NotNull
    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizationManagerRequestMatcherRegistryCustomizer() {
        return authorize -> authorize
//                .requestMatchers(PERMIT_ALL).permitAll()
//                .requestMatchers("/room/**").hasAnyRole("MEMBER", "SUPPORTS", "ADMIN")
//                .requestMatchers(GET, "/**").permitAll()
//                .requestMatchers(POST, "/**").hasAnyRole("MEMBER", "SUPPORTS", "ADMIN")
//                .requestMatchers(PUT, "/**").hasAnyRole("MEMBER", "SUPPORTS", "ADMIN")
                .anyRequest().authenticated();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurerCustomizer() {
        return cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.addAllowedMethod("*"); // Allow all HTTP methods
                configuration.addAllowedHeader("*"); // Allow all headers

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                String origin = request.getHeader("origin");

                configuration.setAllowedOrigins(Collections.singletonList(origin));
                configuration.setAllowCredentials(true);
                return configuration;
            });
        };
    }

    @NotNull
    private static Customizer<SessionManagementConfigurer<HttpSecurity>> getSessionManagementConfigurerCustomizer() {
        return (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @NotNull
    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> getExceptionHandlingConfigurerCustomizer() {
        return (exceptionHandling) ->
                exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }


    // 커스텀 인증 필터
    @Bean
    public CustomAuthenticationFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(customAuthenticationManager());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        return filter;
    }

    // 커스텀 인증 매니저
    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager();
    }


}

