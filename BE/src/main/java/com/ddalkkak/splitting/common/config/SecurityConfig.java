package com.ddalkkak.splitting.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(
//               /*         auth -> auth.requestMatchers("/h2-console/**").permitAll()
//                                .anyRequest().authenticated()*/
//                        auth -> auth.anyRequest().permitAll()
//                ).oauth2Login(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
//
//
//        http.csrf(x -> x.disable());
//
//        // 세션을 Stateless로 변경, 기존의 폼 로그인 방식 비활성화
//        http.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.formLogin(x -> {
//            try {
//                x.disable().httpBasic(c -> c.disable());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 모든 요청 허용, 필요한 경우 주석 처리된 코드로 특정 경로 접근 제어 가능
        http
                .csrf(csrf -> csrf.disable())             // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .oauth2Login(Customizer.withDefaults())   // 기본 OAuth2 로그인 설정
                .headers(headers -> headers.defaultsDisabled()   // 기본 헤더 설정 비활성화
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())) // X-Frame-Options 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 Stateless로 설정
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable); // HTTP Basic 인증 비활성화

        return http.build();
    }
}
