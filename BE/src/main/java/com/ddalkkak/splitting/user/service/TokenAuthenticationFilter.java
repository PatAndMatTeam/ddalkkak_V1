package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.user.domain.User;
import com.ddalkkak.splitting.user.exception.JwtErrorCode;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiPredicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final String[] jwtBypassUrls = {"GET /api/user/login/**",
            "GET /api/user/login/success",
            "GET /api/user/oauth2/**",
            "GET /login/**",
            "GET /api/board/v2/lol/search",
            "GET /api/board/v2/lol/*"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("TokenAuthenticationFilter");
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();

        log.info("RequestMethod: {}, requestURI: {}", requestMethod, requestURI);

        if (shouldBypassFilter(requestMethod, requestURI)) {
            log.info("Bypassing JWT filter for URI: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization");

        if (jwtService.validateToken(accessToken, response)){
            Authentication authentication = jwtService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(t -> jwtService.validateToken(t, response))
                .orElse(null);

        if (refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        if(refreshToken == null){
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldBypassFilter(String requestMethod, String requestURI) {
        return Arrays.stream(jwtBypassUrls)
                .map(this::parseBypassCondition)
                .anyMatch(condition -> condition.test(requestMethod, requestURI));
    }

    private BiPredicate<String, String> parseBypassCondition(String bypassCondition) {
        String[] parts = bypassCondition.split(" ", 2);
        String method = parts[0];
        String uriPattern = parts[1];
        return (methodInput, uriInput) -> method.equalsIgnoreCase(methodInput) && pathMatcher.match(uriPattern, uriInput);
    }



    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        User read = userService.findByRefreshToken(refreshToken);
        String reIssuedRefreshToken = reIssueRefreshToken(read);

        jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(read.getUserId(),
                read.getName()),reIssuedRefreshToken);


        jwtService.sendAccessAndRefreshToken(response,
                jwtService.createAccessToken("test", "test"), "test");
    }



    /**
     * [액세스 토큰 체크 & 인증 처리 메소드]
     * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
     * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
     * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
     * 그 후 다음 인증 필터로 진행
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(t -> jwtService.validateToken(t, response))
                .flatMap(jwtService::extractEmail)
                .map(userService::findByEmail)
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    /**
     * [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     *
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
     * UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
     * 3. Collection < ? extends GrantedAuthority>로,
     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     *
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
     */
    public void saveAuthentication(User user) {
        String password = "test";


        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(password)
                .roles(user.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        userDetailsUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
     * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
     * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
     */
    private String reIssueRefreshToken(com.ddalkkak.splitting.user.domain.User user) {
        String userId = user.getUserId();

        String reIssuedRefreshToken = jwtService.createRefreshToken(userId);
        userService.update(userId, reIssuedRefreshToken);

        return reIssuedRefreshToken;
    }
}
