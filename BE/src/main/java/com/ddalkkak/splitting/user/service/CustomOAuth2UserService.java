package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.Account;
import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    //@Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("oauth2");
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        String registId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttritubeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registId, oAuth2UserAttributes);

        log.info("{}", oAuth2UserInfo);
        Account create = userService.save(oAuth2UserInfo);

        return new PrincipalDetails(create, oAuth2UserAttributes, userNameAttritubeName);
    }

}
