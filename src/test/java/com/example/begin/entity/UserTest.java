package com.example.begin.entity;

import com.example.begin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void userAddTest(){
        User user = User.builder().userId("test2")
                .userPw("12345")
                .nick("test2")
                .email("test2@naver.com")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        System.out.println(user);
    }

}