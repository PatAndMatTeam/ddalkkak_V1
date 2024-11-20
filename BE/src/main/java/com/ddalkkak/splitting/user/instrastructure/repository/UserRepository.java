package com.ddalkkak.splitting.user.instrastructure.repository;

import com.ddalkkak.splitting.user.instrastructure.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUserId(String userId);
    Optional<AccountEntity> findByRefreshToken(String token);



}
