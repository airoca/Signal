package com.example.sign.controller;

import com.example.sign.model.User;
import com.example.sign.service.UserService;
import jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sign.model.SingleSignal;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //모든 사용자
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    //회원 가입
    @PostMapping("/register")
    public User registerUser(@RequestBody User newUser) {
        userService.assignRandomCoordinates(newUser);
        return userService.registerUser(newUser);
    }

    //로그인
    @PostMapping("/login")
    public JwtToken loginUser(@RequestBody User loginDetails) {
        return userService.loginUser(loginDetails.getId(), loginDetails.getPassword());
    }

    //특정 사용자 정보
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 사용자가 다른 사용자에게 시그널 보내기
    @PostMapping("/sendSignal")
    public ResponseEntity<?> sendSignal(@RequestBody SingleSignal singleSignal) {
        userService.sendSignal(singleSignal);
        return ResponseEntity.ok().build();
    }

}
