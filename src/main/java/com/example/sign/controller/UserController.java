package com.example.sign.controller;

import com.example.sign.model.User;
import com.example.sign.service.UserService;
import jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return userService.registerUser(newUser);
    }

    //로그인
    @CrossOrigin(origins="*")
    @PostMapping("/login")
    public JwtToken loginUser(@RequestBody User loginDetails) {
        return userService.loginUser(loginDetails.getId(), loginDetails.getPassword());
    }
}
