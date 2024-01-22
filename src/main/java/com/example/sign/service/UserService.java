package com.example.sign.service;

import com.example.sign.model.DoubleSignal;
import com.example.sign.model.User;
import com.example.sign.repository.UserRepository;
import jwt.JwtToken;
import jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sign.model.SingleSignal;
import com.example.sign.repository.SingleSignalRepository;
import com.example.sign.repository.DoubleSignalRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SingleSignalRepository singleSignalRepository;
    @Autowired
    private DoubleSignalRepository doubleSignalRepository;

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

    public void assignRandomCoordinates(User user) {
        boolean coordinatesAssigned = false;
        while (!coordinatesAssigned) {

            float x = randomCoordinate();
            float y = randomCoordinate();
            float z = randomCoordinate();

            if (isUniqueCoordinate(x, y, z)) {
                user.setX_coordinate(x);
                user.setY_coordinate(y);
                user.setZ_coordinate(z);
                coordinatesAssigned = true;
            }
        }
    }

    private float randomCoordinate() {
        return 50 + (float) Math.random() * 900;
    }

    private boolean isUniqueCoordinate(float x, float y, float z) {
        List<User> allUsers = getAllUsers();
        for (User otherUser : allUsers) {
            Float otherX = otherUser.getX_coordinate();
            Float otherY = otherUser.getY_coordinate();
            Float otherZ = otherUser.getZ_coordinate();

            if (otherX == null || otherY == null || otherZ == null) {
                // 좌표가 아직 설정되지 않은 사용자는 비교에서 제외
                continue;
            }

            if (Math.abs(otherX - x) < 10 &&
                    Math.abs(otherY - y) < 10 &&
                    Math.abs(otherZ - z) < 10) {
                return false;
            }
        }
        return true;
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    //Single Signal
    // UserService 클래스 내

    @Transactional
    public void sendSignal(SingleSignal singleSignal) {
        User receiver = userRepository.findById(singleSignal.getReceiveUser())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // signal이 null일 경우 처리
        Integer currentSignals = receiver.getSignals();
        receiver.setSignals((currentSignals == null) ? 1 : currentSignals + 1);
        userRepository.save(receiver);

        // 쌍방적인 시그널 확인
        boolean reciprocalSignalExists = singleSignalRepository.existsBySendUserAndReceiveUser(
                singleSignal.getReceiveUser(), singleSignal.getSendUser());

        if (reciprocalSignalExists) {
            // 쌍방적인 시그널이 있다면, single_signal에서 삭제하고 double_signal에 추가
            singleSignalRepository.deleteBySendUserAndReceiveUser(
                    singleSignal.getReceiveUser(), singleSignal.getSendUser());

            DoubleSignal doubleSignal = new DoubleSignal();
            doubleSignal.setUser1(singleSignal.getSendUser());
            doubleSignal.setUser2(singleSignal.getReceiveUser());
            doubleSignalRepository.save(doubleSignal);
        } else {
            singleSignalRepository.save(singleSignal);
        }

    }


}
