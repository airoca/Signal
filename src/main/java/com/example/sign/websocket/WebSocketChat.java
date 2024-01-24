package com.example.sign.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ServerEndpoint(value = "/socket/chatt/{user1}/{user2}", configurator = SpringConfigurator.class)
public class WebSocketChat {
    private static final Map<Session, String> sessionRoomMap = new HashMap<>();
    private static final Map<String, Set<Session>> roomSessionMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);

    @Autowired
    private RoomRepository roomRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("user1") String user1, @PathParam("user2") String user2) {

        String roomId;

        // user1과 user2에 해당하는 Room 엔티티를 찾는다.
        Optional<Room> existingRoom = roomRepository.findByUser1AndUser2(user1, user2);
        if(!existingRoom.isPresent()){
            existingRoom = roomRepository.findByUser1AndUser2(user2, user1);
        }
        if (existingRoom.isPresent()) {
            // 존재하는 경우, roomId를 가져온다.
            roomId = existingRoom.get().getRoomId();
            logger.info("There was existing roomId: {}", roomId);
        } else {
            // 존재하지 않는 경우, 새로운 Room을 생성한다.
            roomId = RandomStringGenerator.generateRandomString();
            Room room = new Room();
            room.setRoomId(roomId);
            room.setUser1(user1);
            room.setUser2(user2);
            roomRepository.save(room);
        }

        sessionRoomMap.put(session, roomId);
        roomSessionMap.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);

        logger.info("Session opened in room: {}", roomId);

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String roomId = sessionRoomMap.get(session);
        Set<Session> sessionsInRoom = roomSessionMap.get(roomId);

        // 클라이언트로부터 받은 메시지를 로그에 기록
        logger.info("Message from client in room [{}]: {}", roomId, message);

        for (Session s : sessionsInRoom) {
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("Session close : {}", session);

        String roomId = sessionRoomMap.get(session);
        sessionRoomMap.remove(session);

        Set<Session> sessionsInRoom = roomSessionMap.get(roomId);
        sessionsInRoom.remove(session);

        if (sessionsInRoom.isEmpty()) {
            roomSessionMap.remove(roomId);
        }
    }
}