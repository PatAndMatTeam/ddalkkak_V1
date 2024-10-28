package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.User;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private static final String URI = "/api/user/login/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("onAuthenticationSuccess");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        User user = principal.user();

        String accessToken = jwtService.createAccessToken(user.getUserId(), user.getName());
        String refreshToken = jwtService.createRefreshToken(authentication.getName());

        log.info("getDetails {}", authentication.getDetails());
        log.info("getCredentials {}", authentication.getCredentials());

        log.info("{}", user);
        setCookie(response, "accessToken", accessToken); // 1시간
        setCookie(response, "refreshToken", refreshToken); // 7일

        response.sendRedirect(URI);
    }

    private void setCookie(HttpServletResponse response,String tokenName, String token) {
        ResponseCookie responseCookie = ResponseCookie.from(tokenName, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")  // SameSite 설정 가능
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }
}
