package com.ddalkkak.splitting.user.service;

import com.ddalkkak.splitting.user.domain.User;
import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        String registId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttritubeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registId, oAuth2UserAttributes);

        //User user = saveUser(oAuth2UserInfo);

        return new PrincipalDetails(new User(),oAuth2UserAttributes, userNameAttritubeName);
    }

}
