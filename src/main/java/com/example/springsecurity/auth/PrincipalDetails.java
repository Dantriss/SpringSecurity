package com.example.springsecurity.auth;


import com.example.springsecurity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


// 시큐리티가 /login주소 요청이 오면 낚아채서 로그인을 진행
// 로그인을 진행이 완료가 되면 (Security ContextHolder)에 session을 만들어줌
// 세션에 들어갈수 있는 오브젝트 -> Authentication 타입 객체로 정해져있음
// Authentication 안에 user 정보가 있어야 하는데  -> UserDetails 타입 객체로 정해져있음

// Security Session -> Authentication -> UserDetails (principalDetails에 userDetails를 오버라이딩해서 사용가능하게 함)
public class PrincipalDetails implements UserDetails {

    private User user;  //컴포지션

    public PrincipalDetails(User user){
        this.user=user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    // 만료여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 계정잠금여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 비밀번호 변경기간 유효여부
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 계정활성화여부(휴먼계정)
    public boolean isEnabled() {
        return true;
    }
}
