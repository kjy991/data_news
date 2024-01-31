package com.collecting.collecting_data_news.security.provider;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements Serializable {

    private final SecretKey SECRET_KEY;
    private final long EXPIRATION_TIME;

    private SecretKey getSECRET_KEY() {
        return SECRET_KEY;
    }

    private long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    /**
     * Jwt 생성
     */
    public String generateToken(Member member) {
        long expirationTime = getExpirationTime();

        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getGrade().label()));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + getExpirationTime());
        return Jwts.builder()
                .setSubject(member.getIdx().toString())
                .claim("authorities", roles.stream().map(GrantedAuthority::getAuthority).collect(toList()))
                .claim("nickname", member.getNickname())  // 이 부분을 추가하여 nickname 클레임을 설정합니다
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, getSECRET_KEY())
                .compact();
    }

    /**
     * JWT 회원 정보 추출.
     */
    public Claims getUsernameFromToken(String token, HttpServletRequest request) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSECRET_KEY())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            request.setAttribute(JWT_EXCEPTION.label(), EXPIRED_JWT_EXCEPTION);
        } catch (MalformedJwtException e) {
            request.setAttribute(JWT_EXCEPTION.label(), MALFORMED_JWT_EXCEPTION);
        } catch (UnsupportedJwtException e) {
            request.setAttribute(JWT_EXCEPTION.label(), UNSUPPORTED_JWT_EXCEPTION);
        } catch (SignatureException e) {
            request.setAttribute(JWT_EXCEPTION.label(), SIGNATURE_EXCEPTION);
        } catch (IllegalArgumentException e) {
            request.setAttribute(JWT_EXCEPTION.label(), ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (NullPointerException e) {
            request.setAttribute(JWT_EXCEPTION.label(), NULL_POINT_EXCEPTION);
        }
        return claims;
    }



    @NotNull
    public String getClaims(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String jwtToken = requestTokenHeader.substring(7);
        Claims claims = getUsernameFromToken(jwtToken, request);
        claims.getSubject();
        return (String) ((ArrayList<?>) claims.get("authorities")).get(0);
    }
}

