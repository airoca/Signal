package com.example.sign.controller;

import com.example.sign.model.User;
import com.example.sign.service.UserService;
import jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sign.model.SingleSignal;

import java.util.List;
import java.util.Map;

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

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Map<String, Object> response = userService.loginUser(loginDetails.getId(), loginDetails.getPassword());
        return ResponseEntity.ok(response);
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

    // 사용자가 다른 사용자에게 보낸 시그널 삭제
    @PostMapping("/deleteSignal")
    public ResponseEntity<?> deleteSignal(@RequestBody SingleSignal singleSignal) {
        userService.removeSignal(singleSignal.getSendUser(), singleSignal.getReceiveUser());
        return ResponseEntity.ok().build();
    }


}
