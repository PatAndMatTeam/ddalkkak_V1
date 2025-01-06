package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @DisplayName("토큰을 갖고 있는 유저가 없으면 Exception이다.")
    @Test
    public void getUser(){
        String accessToken = "test";

        assertThrows(UserException.NotExistAccessTokenException.class, () -> {
            userService.find(accessToken);
        });
    }
}
