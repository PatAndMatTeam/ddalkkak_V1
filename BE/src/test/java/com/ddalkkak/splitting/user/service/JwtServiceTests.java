package com.ddalkkak.splitting.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class JwtServiceTests {

    @DisplayName("refresh 토큰 헤더 정보가 없으면 빈값을 반환한다.")
    @Test
    public void test(){
        //given
        String header = null;

        //when
        Optional<String> target =  Optional.ofNullable(header)
                .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));

        //then
        Assertions.assertEquals(target, Optional.empty());
    }
}
