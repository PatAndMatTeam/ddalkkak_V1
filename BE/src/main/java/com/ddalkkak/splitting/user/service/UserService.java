package com.ddalkkak.splitting.user.service;


import com.ddalkkak.splitting.user.api.request.UserPasswordVerifyRequest;
import com.ddalkkak.splitting.user.exception.UserErrorCode;
import com.ddalkkak.splitting.user.exception.UserException;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean verifyPassword(UserPasswordVerifyRequest passwordVerifyRequest){
        String userPw = userRepository.findByUserId(passwordVerifyRequest.userId()).getUserPw();
        if (!passwordVerifyRequest.password().equals(userPw)){
            return false;
        }
        return true;
    }

}
