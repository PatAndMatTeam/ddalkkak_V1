package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${kakao.login.success.client-redirect-uri}")
    private static String loginSuccessRedirectUrl;
    private final JwtService jwtService;

    private static final String URI = "/api/user/login/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("onAuthenticationSuccess");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Account account = principal.account();

        String accessToken = jwtService.createAccessToken(account.getUserId(), account.getName());
        String refreshToken = jwtService.createRefreshToken(authentication.getName());

        log.info("getDetails {}", authentication.getDetails());
        log.info("getCredentials {}", authentication.getCredentials());

        log.info("{}", account);
        setCookie(response, "accessToken", accessToken); // 10분
        setCookie(response, "refreshToken", refreshToken); // 1시간

        //response.sendRedirect(URI);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\": \"Authentication successful\"}");
        response.sendRedirect(loginSuccessRedirectUrl);
    }

    private void setCookie(HttpServletResponse response,String tokenName, String token) {
        ResponseCookie responseCookie = ResponseCookie.from(tokenName, token)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")  // SameSite 설정 가능
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }
}
