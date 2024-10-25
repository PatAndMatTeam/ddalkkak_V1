package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.User;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("TokenAuthenticationFilter");
        String requestURI = request.getRequestURI();
        logger.info(requestURI);

        if (requestURI.equals("/api/user/login")){
            filterChain.doFilter(request, response);
            return;
        }

//           String token = request.getHeader("Authorization");
//
//           if(jwtService.validateToken(token)){
//               Authentication authentication = jwtService.getAuthentication(token);
//               SecurityContextHolder.getContext().setAuthentication(authentication);
//           }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::validateToken)
                .orElse(null);

        // 리프레시 토큰이 요청 헤더에 존재했다면, 사용자가 AccessToken이 만료되어서
        // RefreshToken까지 보낸 것이므로 리프레시 토큰이 DB의 리프레시 토큰과 일치하는지 판단 후,
        // 일치한다면 AccessToken을 재발급해준다.
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return; // RefreshToken을 보낸 경우에는 AccessToken을 재발급 하고 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
        }

        // RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
        // AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403 에러 발생
        // AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }


    }


    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
//        userRepository.findByRefreshToken(refreshToken)
//                .ifPresent(user -> {
//                    String reIssuedRefreshToken = reIssueRefreshToken(user);
//                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getEmail()),
//                            reIssuedRefreshToken);
//                });

        jwtService.sendAccessAndRefreshToken(response,
                jwtService.createAccessToken("test"), "test");
    }

    /**
     * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
     * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
     * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
     */
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken(user.getUserId());
//        user.updateRefreshToken(reIssuedRefreshToken);
//        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
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
//        jwtService.extractAccessToken(request)
//                .filter(jwtService::validateToken)
//                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
//                        .ifPresent(email -> userRepository.findByEmail(email)
//                                .ifPresent(this::saveAuthentication)));

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
//    public void saveAuthentication(User myUser) {
//        String password = myUser.getPassword();
//        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
//            password = PasswordUtil.generateRandomPassword();
//        }
//
//        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
//                .username(myUser.getEmail())
//                .password(password)
//                .roles(myUser.getRole().name())
//                .build();
//
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
//                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
}
