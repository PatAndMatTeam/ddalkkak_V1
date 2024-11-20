package com.ddalkkak.splitting.common.config;

import com.ddalkkak.splitting.user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");  // 허용할 출처
        configuration.addAllowedMethod("GET");  // 허용할 HTTP 메소드
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedHeader("*");  // 모든 헤더 허용
        configuration.setAllowCredentials(true);  // 자격 증명 허용
        configuration.setMaxAge(3600L);  // CORS 응답 캐시 시간 설정 (1시간)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 적용
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 모든 요청 허용, 필요한 경우 주석 처리된 코드로 특정 경로 접근 제어 가능
        http
                .csrf(csrf -> csrf.disable())             // CSRF 보호 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) //cors off
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
               // .antMatchers("/login/**", "/oauth2/**").permitAll()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login/**", "/api/user/login/success/**","/api/user/oauth2/**", "/login/**").permitAll() // 특정 경로 허용
                        .requestMatchers(HttpMethod.GET, "/api/board/v2/lol/all",
                                "/api/board/v2/lol/search",
                                "/api/board/v2/lol/**",
                                "/api/user/token").permitAll()
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/board/v2/*/vote",
                                "/api/board/v2/**",
                                "/api/board/v2/*/recommend").permitAll()
                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
                 .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtExceptionFilter))

                .headers(headers -> headers.defaultsDisabled()   // 기본 헤더 설정 비활성화
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())) // X-Frame-Options 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 Stateless로 설정

                .oauth2Login(oauth -> oauth.loginPage("/api/user/login"))
                .oauth2Login(oauth -> oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))

                .oauth2Login(oauth -> oauth.redirectionEndpoint(x -> x.baseUri("/login/oauth2/code/*"))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler))

//                .logout(x -> x.clearAuthentication(true))
//                .logout(x -> x.deleteCookies("JSESSIONID"))

                //jwt
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
