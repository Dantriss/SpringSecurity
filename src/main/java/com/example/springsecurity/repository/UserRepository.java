package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 가지고 있음
//JpaRePository를 상속했기 때문에, @Repository라는 어노테이션이 없어도 IoC가 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

    //FindBy -> 규칙, Username -> 문법
    // == select * from User where username = 1?(username)
    public User findByUsername(String username);
}
