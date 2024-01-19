package com.example.sign.service;

import com.example.sign.model.User;
import com.example.sign.repository.UserRepository;
import jwt.JwtToken;
import jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //모든 회원 정보
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 회원 가입 메서드 수정
    public User registerUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    // 로그인 메서드 수정
    public JwtToken loginUser(String id, String password) {
        return userRepository.findById(id)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(id, null)))
                .orElseThrow(() -> new RuntimeException("로그인 실패"));
    }
}
