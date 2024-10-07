package com.ddalkkak.splitting.user.api;


import com.ddalkkak.splitting.user.api.request.UserPasswordVerifyRequest;
import com.ddalkkak.splitting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;


    //네이버|카카오|구글 로그인


    //비밀번호 일치 여부
    @PostMapping("check-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody UserPasswordVerifyRequest passwordVerifyRequest){
        boolean response = userService.verifyPassword(passwordVerifyRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
