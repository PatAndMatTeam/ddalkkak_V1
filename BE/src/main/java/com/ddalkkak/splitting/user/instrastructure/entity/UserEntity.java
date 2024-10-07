package com.ddalkkak.splitting.user.instrastructure.entity;

//import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
//import com.ddalkkak.splitting.user.dto.RoleUser;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Table(name="USERS")
//@Entity
//public class UserEntity extends BaseTimeEntity implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String userId;
//
//    private String userPw;
//
//
//    private String nickname;
//
//    private String ip;
//
//    private String provider;
//
//    private RoleUser role;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//
//        for(String role : role.getValue().split(",")){
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return userPw;
//    }
//
//    @Override
//    public String getUsername() {
//        return nickname;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}



import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import com.ddalkkak.splitting.user.dto.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="USERS")
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String userPw;


    private String nickname;

    private String ip;

    private String provider;

    private RoleUser role;

}
