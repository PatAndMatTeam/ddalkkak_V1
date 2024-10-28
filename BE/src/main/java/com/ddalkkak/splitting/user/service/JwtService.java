package com.ddalkkak.splitting.user.service;


import com.ddalkkak.splitting.user.exception.JwtErroCode;
import com.ddalkkak.splitting.user.exception.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;


@Slf4j
@Service
public class JwtService {

    private int TOKEN_EXPIRE_TIME = 1000;
    private int REFRESH_TOKEN_EXPIRE_TIME = 1000;
    private String secretKey = "YOONJUYOUNGISVERYHANSOMEGUYANDHEISVERYSMARYANDSEXY";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    //@Value("${jwt.access.header}")
    private static final String accessHeader = "Authorization";

    //@Value("${jwt.refresh.header}")
    private static final String refreshHeader = "Authorization-refresh";

    private static final String BEARER = "Bearer ";

    public String createAccessToken(String userEmail, String name){
        Claims claims = Jwts.claims()
                .add("name", name)
                .add("email", userEmail)
                .build();

        Date now = new Date();

        String token = Jwts.builder()
                .subject(ACCESS_TOKEN_SUBJECT)
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .signWith(getSignKey())
                .compact();

        return token;
    }

    public com.ddalkkak.splitting.user.domain.User extractUserInfo(String token) {
        // 토큰을 파싱하여 클레임을 가져옴
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // 사용자 이름과 이메일 추출
        String userName = (String) claims.get("name"); // 'name' 클레임에서 사용자 이름
        String userEmail = (String) claims.get("email"); // 'email' 클레임에서 사용자 이메일

        return com.ddalkkak.splitting.user.domain.User.builder()
                .userId(userEmail)
                .name(userName)
                .build();
    }


    private Key getSignKey(){
        byte[] keyBytes = secretKey.getBytes(); // String을 byte 배열로 변환
        return new SecretKeySpec(keyBytes, HS256.getJcaName()); // SecretKey 생성
    }

    public boolean validateToken(String token) {
        var parser = Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build();
        try {
            var result = parser.parseSignedClaims(token);
            result.getPayload().forEach((key1, value1) -> log.info("key : {}, value : {}", key1, value1));
        } catch (SignatureException e) {
            throw new RuntimeException("JWT Token Invalid Exception");
        }catch (ExpiredJwtException e){
            log.warn("JWT Token Expired Exception");
            throw  new JwtException.ExpiredActiveTokenException(JwtErroCode.EXPIRED_ACCESS_TOKEN, token);
        }
        return true;
    }

    public String createRefreshToken(String userEmail) {
        Claims claims = Jwts.claims().subject(userEmail)
                .build();
        Date now = new Date();

        Map map = new HashMap();
        map.put("email", claims);

        String token = Jwts.builder()
                .subject(REFRESH_TOKEN_SUBJECT)
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .signWith(getSignKey())
                .compact();

        return token;
        //tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken); // redis에 저장
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }


    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
//            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
//                    .build() // 반환된 빌더로 JWT verifier 생성
//                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
//                    .getClaim() // claim(Emial) 가져오기
//                    .asString());
            return Optional.ofNullable(Jwts.parser()
                    .verifyWith((SecretKey) getSignKey())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload().getSubject());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

}
