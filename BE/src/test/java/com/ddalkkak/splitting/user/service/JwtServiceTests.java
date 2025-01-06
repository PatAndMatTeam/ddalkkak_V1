package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.Account;
import com.ddalkkak.splitting.user.dto.RoleUser;
import com.ddalkkak.splitting.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtServiceTests {

    @MockBean
    private UserService userService;  // userService를 mock합니다.

    @Autowired
    private JwtService jwtService;  // 테스트할 TokenService


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
        assertEquals(target, Optional.empty());
    }

    @DisplayName("AccessToken과 refreshToken의 email값은 같다.")
    @Test
    public void tokenDiffTest(){
        //given
        String email = "jyyoon@gmail.com";
        String name = "윤주영";
        String createToken = jwtService.createAccessToken(email, name);

        Account account = Account.builder()
                .userId(email)
                .name(name)
                .role(RoleUser.USER)
                .provider("네이버")
                .build();

        when(userService.find(createToken)).thenReturn(account);

        String refreshToken = jwtService.refreshAccressToken(createToken);

        assertNotNull(refreshToken);  // 결과 토큰이 null이 아님을 확인
        assertEquals(jwtService.extractEmail(createToken),
                jwtService.extractEmail(refreshToken));
    }

    @DisplayName("DB에 AccessToken이 있다면 RefreshToken을 발급 받을 수 있다.")
    @Test
    public void successedRefreshToken(){
        //given
        String email = "jyyoon@gmail.com";
        String name = "윤주영";
        String createToken = jwtService.createAccessToken(email, name);

        Account account = Account.builder()
                .userId(email)
                .name(name)
                .role(RoleUser.USER)
                .provider("네이버")
                .build();

        String refreshToken = jwtService.refreshAccressToken(createToken);

        assertNotNull(refreshToken);  // 결과 토큰이 null이 아님을 확인
        assertEquals(jwtService.extractEmail(createToken),
                jwtService.extractEmail(refreshToken));
    }
}
