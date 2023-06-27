package com.example.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴할것이다.
public class indexController {

    @GetMapping({ "", "/" })
    public String index(){
        System.out.println("controller test");
        //mustache 기본폴더  src/main/resources/
        //viewResolver : templates(prefix), mustaches(suffix) 생략가능
        return "index"; //src/main/resources/templates/index.java
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
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
    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }
    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입완료";
    }

}
