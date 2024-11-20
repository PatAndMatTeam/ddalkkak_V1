package com.ddalkkak.splitting.user.service;


import com.ddalkkak.splitting.user.api.request.UserPasswordVerifyRequest;
import com.ddalkkak.splitting.user.domain.Account;
import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import com.ddalkkak.splitting.user.exception.UserErrorCode;
import com.ddalkkak.splitting.user.exception.UserException;
import com.ddalkkak.splitting.user.instrastructure.entity.AccountEntity;
import com.ddalkkak.splitting.user.instrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    public Account save(OAuth2UserInfo user){
        Account create = Account.from(user);
        return userRepository.save(AccountEntity
                        .fromModel(create)).toModel();
    }

    public void update(String userId, String refreshToken){
        AccountEntity user = userRepository.findByUserId(userId).get();

        user.changeRefreshToken(refreshToken);
    }

    public Account find(String refreshToken){
        return userRepository.findByRefreshToken(refreshToken)
                .get()
                .toModel();
    }

    public Account findByRefreshToken(final String refreshToken){
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UserException.InvalidPasswordException(UserErrorCode.INVALID_PASSWORD, 1l))
                .toModel();
    }

    public Account findByEmail(final String email){
        return userRepository.findByUserId(email)
                .orElseThrow(() -> new UserException.InvalidPasswordException(UserErrorCode.INVALID_PASSWORD, 1l))
                .toModel();
    }


}
