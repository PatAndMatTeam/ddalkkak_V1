package com.ddalkkak.splitting.user.api;

import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserApiTests {

    final String clientId = "4b7adc32013accf7909b512bd1f252ac";
    final String redirectUrl = "http://localhost:8082/login/callback";

    final String code = "KC5S3SXDEyZq4aP8sRTH24IdBqq-RCjdriuBgNyyHqQat-KaLsdKggAAAAQKKw0eAAABkq4qna9Udd9ffL_GXA&state=3XKN49-8LMah4XougTadSoAyA7OASqEtkRJS-Hc7R0E%3D";

    @Test
    void test() throws JsonProcessingException {
        //https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=clientId&redirect_uri=redirectUrl&code=code

        RestClient restClient = RestClient.create();

        String result = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="+clientId+"&redirect_uri="+redirectUrl+"&code="+code)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode parsedResp = mapper.readTree(result);

        // 액세스 토큰으로 유저를 찾는다.
        String accessToken = parsedResp.get("access_token").asText();
 /*
 {
            "access_token": "Cu5i_ajleWcFYcdGA7gGdGIOyoqnPx-gAAAAAQorDR4AAAGSrisc6UPPWzORmYVE",
            "token_type": "bearer",
            "refresh_token": "3Q0kkiX4eFagK0IvVpZijfYTV6bVd8ezAAAAAgorDR4AAAGSrisc4kPPWzORmYVE",
            "expires_in": 21599,
            "scope": "account_email profile_nickname",
            "refresh_token_expires_in": 5183999
        }
        */
        System.out.println(result);

        RestClient restClient1 = RestClient.create();
        //	https://kapi.kakao.com/v2/user/me

        //info =
/*            {
                "id": 3759567530,
                    "connected_at": "2024-10-21T08:14:18Z",
                    "properties": {
                "nickname": "주영"
            },
                "kakao_account": {
                "profile_nickname_needs_agreement": false,
                        "profile": {
                    "nickname": "주영",
                            "is_default_nickname": false
                },
                "has_email": true,
                        "email_needs_agreement": false,
                        "is_email_valid": true,
                        "is_email_verified": true,
                        "email": "pulpul8282@naver.com"
            }
            }*/
        String info = restClient1.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer "+accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(String.class);

        System.out.println("info = " + info);
    }

    @Test
    public void test1(){
        String test = "1731578925102";

        Long time = Long.parseLong(test);

        System.out.println("time = " + time);
    }
}
