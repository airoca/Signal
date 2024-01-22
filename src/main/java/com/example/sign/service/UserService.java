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

import java.util.*;
import java.util.stream.Collectors;

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
        assignRandomColor(newUser);
        return userRepository.save(newUser);
    }

    // 로그인 메서드 수정
    public Map<String, Object> loginUser(String id, String password) {
        User user = userRepository.findById(id)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new RuntimeException("로그인 실패"));

        JwtToken jwtToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(id, null));

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", jwtToken);

        return response;
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

    // 색상 할당 메서드
    private void assignRandomColor(User user) {
        String[] colors = {"#88beff", "white", "#f9d397", "#fd6b6b", "#ffffac"};
        Random rand = new Random();
        String randomColor = colors[rand.nextInt(colors.length)];
        user.setColor(randomColor);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

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

            User user1 = userRepository.findById(singleSignal.getSendUser())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            User user2 = userRepository.findById(singleSignal.getReceiveUser())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            DoubleSignal doubleSignal = new DoubleSignal();
            doubleSignal.setUser1(user1.getId());
            doubleSignal.setUser2(user2.getId());
            doubleSignal.setUser1XCoordinate(user1.getX_coordinate());
            doubleSignal.setUser1YCoordinate(user1.getY_coordinate());
            doubleSignal.setUser1ZCoordinate(user1.getZ_coordinate());
            doubleSignal.setUser2XCoordinate(user2.getX_coordinate());
            doubleSignal.setUser2YCoordinate(user2.getY_coordinate());
            doubleSignal.setUser2ZCoordinate(user2.getZ_coordinate());

            doubleSignalRepository.save(doubleSignal);
        } else {
            singleSignalRepository.save(singleSignal);
        }

    }

    @Transactional
    public void removeSignal(String senderId, String receiverId) {
        // 쌍방적인 시그널이 있는지 확인
        boolean isDoubleSignal = doubleSignalRepository.existsByUser1AndUser2(senderId, receiverId)
                || doubleSignalRepository.existsByUser1AndUser2(receiverId, senderId);

        User affectedUser = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // signal 감소
        Integer currentSignals = affectedUser.getSignals();
        affectedUser.setSignals((currentSignals == null || currentSignals <= 0) ? 0 : currentSignals - 1);
        userRepository.save(affectedUser);

        if (isDoubleSignal) {
            // 쌍방적인 시그널을 삭제하고, single_signal 테이블에 새로운 정보 추가
            doubleSignalRepository.deleteByUser1AndUser2(senderId, receiverId);
            doubleSignalRepository.deleteByUser1AndUser2(receiverId, senderId);

            SingleSignal newSingleSignal = new SingleSignal();
            newSingleSignal.setSendUser(receiverId); // 삭제된 user가 send_user.
            newSingleSignal.setReceiveUser(senderId); // 삭제한 user가 receive_user
            singleSignalRepository.save(newSingleSignal);
        } else {
            // 일방적인 시그널을 단순히 삭제
            singleSignalRepository.deleteBySendUserAndReceiveUser(senderId, receiverId);
        }
    }

    public List<User> getReceivedSignalUsers(String userId) {
        Set<String> senderIds = new HashSet<>();

        // single_signal 테이블에서 받은 신호
        List<SingleSignal> receivedSingleSignals = singleSignalRepository.findByReceiveUser(userId);
        senderIds.addAll(receivedSingleSignals.stream()
                .map(SingleSignal::getSendUser)
                .collect(Collectors.toSet()));

        // double_signal 테이블에서 받은 신호
        List<DoubleSignal> receivedDoubleSignals = doubleSignalRepository.findByUser1OrUser2(userId, userId);
        for (DoubleSignal signal : receivedDoubleSignals) {
            senderIds.add(signal.getUser1().equals(userId) ? signal.getUser2() : signal.getUser1());
        }

        return senderIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<User> getSentSignalUsers(String userId) {
        Set<String> receiverIds = new HashSet<>();

        // single_signal 테이블에서 보낸 신호
        List<SingleSignal> sentSingleSignals = singleSignalRepository.findBySendUser(userId);
        receiverIds.addAll(sentSingleSignals.stream()
                .map(SingleSignal::getReceiveUser)
                .collect(Collectors.toSet()));

        // double_signal 테이블에서 보낸 신호
        List<DoubleSignal> sentDoubleSignals = doubleSignalRepository.findByUser1OrUser2(userId, userId);
        for (DoubleSignal signal : sentDoubleSignals) {
            receiverIds.add(signal.getUser1().equals(userId) ? signal.getUser2() : signal.getUser1());
        }

        return receiverIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }



}
