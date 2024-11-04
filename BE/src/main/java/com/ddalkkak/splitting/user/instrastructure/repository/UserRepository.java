package com.ddalkkak.splitting.user.instrastructure.repository;

import com.ddalkkak.splitting.user.instrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByRefreshToken(String token);



}
