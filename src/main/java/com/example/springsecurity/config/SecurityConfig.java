package com.example.springsecurity.config;

import com.example.springsecurity.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //메모리에 띄우기 위해서 어노테이션 추가
@EnableWebSecurity  //활성화 하면 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;



    @Bean   //Bean 등록하면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.

    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()

                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")  //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행해줌 -> controller /login 만들필요 없음
                    .defaultSuccessUrl("/")

                .and()
                    .oauth2Login()
                    .loginPage("/loginForm")                                    // 카카오 : 1. 코드받기(인증) 2. 엑세스토큰(권한) 3.사용자프로필 정보를 가져옴 4-1. 정보를 토대로 회원가입을 자동진행 4-2. 추가적으로 필요한 정보는 추가회원가입
                                                                                // 구글 : 코드를 받지 않고 엑세스토큰+사용자프로필정보를 한번에 받음
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);


        return http.build();

    }


}
