package com.example.springsecurity.controller;

import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller //view를 리턴할것이다.
public class indexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping({ "", "/" })
    public String index(){
        System.out.println("controller test");
        //mustache 기본폴더  src/main/resources/
        //viewResolver : templates(prefix), mustaches(suffix) 생략가능
        return "index"; //src/main/resources/templates/index.java
    }

    @GetMapping("/user")
    public @ResponseBody String user(Authentication authentication){

        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println("test : "+user);
        user.setRole("ROLE_USER");

        //비밀번호 해시화
        String rawPassword = user.getPassword();
        String endPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(endPassword);

        if (user.getUsername().equals("manager")){
            user.setRole("ROLE_MANAGER");
        }else if (user.getUsername().equals("admin")){
            user.setRole("ROLE_ADMIN");
        }


        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info (){

        return "어드민만 볼 수 있습니다";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/info2")
    public @ResponseBody String info2 (){

        return "어드민과 매니저만 볼 수 있습니다";
    }

}
