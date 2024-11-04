package com.ddalkkak.splitting.user.service;


import com.ddalkkak.splitting.user.api.request.UserPasswordVerifyRequest;
import com.ddalkkak.splitting.user.domain.User;
import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import com.ddalkkak.splitting.user.exception.UserErrorCode;
import com.ddalkkak.splitting.user.exception.UserException;
import com.ddalkkak.splitting.user.instrastructure.entity.UserEntity;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean verifyPassword(UserPasswordVerifyRequest passwordVerifyRequest){
        String userPw = userRepository.findByUserId(passwordVerifyRequest.userId()).get().getUserPw();
        if (!passwordVerifyRequest.password().equals(userPw)){
            return false;
        }
        return true;
    }


    public User save(OAuth2UserInfo user){
        User create = User.from(user);
        return userRepository.save(UserEntity
                        .fromModel(create)
                ).toModel();
    }

    public void update(String userId, String refreshToken){
        UserEntity user = userRepository.findByUserId(userId).get();

        user.changeRefreshToken(refreshToken);
    }

    public User findByRefreshToken(final String refreshToken){
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UserException.InvalidPasswordException(UserErrorCode.INVALID_PASSWORD, 1l))
                .toModel();
    }

    public User findByEmail(final String email){
        return userRepository.findByUserId(email)
                .orElseThrow(() -> new UserException.InvalidPasswordException(UserErrorCode.INVALID_PASSWORD, 1l))
                .toModel();
    }


}
