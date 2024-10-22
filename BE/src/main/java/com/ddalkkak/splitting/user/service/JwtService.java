package com.ddalkkak.splitting.user.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;


@Slf4j
@Service
public class JwtService {

    private int TOKEN_EXPIRE_TIME = 1000;
    private int REFRESH_TOKEN_EXPIRE_TIME = 1000;
    private String secretKey = "hansomeYJY";

    public String createToken(Authentication authentication){
        Claims claims = Jwts.claims().subject(authentication.getName()).build();
        Date now = new Date();

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .signWith(getSignKey())
                .compact();

        return token;
    }

    private Key getSignKey(){
        byte[] keyBytes = secretKey.getBytes(); // String을 byte 배열로 변환
        return new SecretKeySpec(keyBytes, HS256.getJcaName()); // SecretKey 생성
    }

    public void validateToken(String token) {
        var parser = Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build();

        try {
            var result = parser.parseSignedClaims(token);
            result.getPayload().forEach((key1, value1) -> log.info("key : {}, value : {}", key1, value1));
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                throw new RuntimeException("JWT Token Invalid Exception");
            } else if (e instanceof ExpiredJwtException) {
                throw new RuntimeException("JWT Token Expired Exception");
            } else {
                throw new RuntimeException("JWT Exception");
            }
        }
    }

    public void createRefreshToken(Authentication authentication, String accessToken) {
        String refreshToken = createToken(authentication);
        //tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken); // redis에 저장
    }

}
