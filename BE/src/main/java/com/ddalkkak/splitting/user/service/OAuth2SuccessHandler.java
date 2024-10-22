package com.ddalkkak.splitting.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private static final String URI = "/auth/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String accessToken = jwtService.createToken(authentication);
        jwtService.createRefreshToken(authentication, accessToken);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}