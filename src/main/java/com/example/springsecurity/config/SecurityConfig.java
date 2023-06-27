package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //메모리에 띄우기 위해서 어노테이션 추가
@EnableWebSecurity  //활성화 하면 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
public class SecurityConfig  {



    @Bean   //Bean 등록하면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.

    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")  //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행해줌 -> controller /login 만들필요 없음
                .defaultSuccessUrl("/");
        return http.build();

    }


}
