package com.ddalkkak.splitting.user.api;


import com.ddalkkak.splitting.user.api.request.UserPasswordVerifyRequest;
import com.ddalkkak.splitting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authUrl;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;

    //네이버|카카오|구글 로그인
    @GetMapping("/login")
    public String login(){
        return "redirect:"+authUrl+"?client_id="+clientId+"&redirect_uri="+redirectUrl+"&response_type=code";
    }

    @GetMapping("/login/callback")
    public String loginCallBack(@RequestParam(required = false) String code,
                                @RequestParam(required = false) String error){
        return "redirect:"+authUrl+"?client_id="+clientId+"&redirect_uri="+redirectUrl+"&response_type=code";
    }

    //비밀번호 일치 여부
    @PostMapping("check-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody UserPasswordVerifyRequest passwordVerifyRequest){
        boolean response = userService.verifyPassword(passwordVerifyRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
