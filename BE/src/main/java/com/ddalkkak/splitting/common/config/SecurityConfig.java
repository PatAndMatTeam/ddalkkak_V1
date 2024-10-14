package com.ddalkkak.splitting.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth -> auth.requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));


        http.csrf(x -> x.disable());

        // 세션을 Stateless로 변경, 기존의 폼 로그인 방식 비활성화
        http.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(x -> {
            try {
                x.disable().httpBasic(c -> c.disable());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return http.build();
    }
}
