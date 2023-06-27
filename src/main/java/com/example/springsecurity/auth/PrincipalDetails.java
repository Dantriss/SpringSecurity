package com.example.springsecurity.auth;


import com.example.springsecurity.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


// 시큐리티가 /login주소 요청이 오면 낚아채서 로그인을 진행
// 로그인을 진행이 완료가 되면 (Security ContextHolder)에 session을 만들어줌
// 세션에 들어갈수 있는 오브젝트 -> Authentication 타입 객체로 정해져있음
// Authentication 안에 user 정보가 있어야 하는데  -> UserDetails 타입 객체로 정해져있음

// Security Session -> Authentication -> UserDetails (principalDetails에 userDetails를 오버라이딩해서 사용가능하게 함)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;  //컴포지션
    private Map<String,Object> attributes;

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
    // OAuth로그인 생성자
    public PrincipalDetails(User user, Map<String,Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }


    // 일반로그인 생성자
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

        return collection;

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
