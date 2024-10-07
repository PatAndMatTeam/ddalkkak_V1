package com.ddalkkak.splitting.user.service;

//import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
//import com.ddalkkak.splitting.user.instrastructure.entity.UserEntity;
//import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class OAuth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // 1. OAuth2 로그인 유저 정보를 가져옴
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // 2. provider : kakao, naver, google
//        String provider = userRequest.getClientRegistration().getRegistrationId();
//
//        // 3. 필요한 정보를 provider에 따라 다르게 mapping
//        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, oAuth2User.getAttributes());
//
//        // 4. oAuth2UserInfo가 저장되어 있는지 유저 정보 확인
//        //    없으면 DB 저장 후 해당 유저를 저장
//        //    있으면 해당 유저를 저장
//        UserEntity user = userRepository.findByUserId(oAuth2UserInfo.email())
//                .orElseGet(() -> userRepository.save(oAuth2UserInfo.toEntity()));
//        log.info("user : {}", user.toString());
//
//        // 5. UserDetails와 OAuth2User를 다중 상속한 CustomUserDetails
//        return oAuth2User;
//    }
//}
