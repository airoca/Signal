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
    @Autowired
    private RoomDetailRepository roomDetailRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("user1") String user1, @PathParam("user2") String user2) throws IOException {
        String roomId;

        // user1과 user2에 해당하는 Room
        Optional<Room> existingRoom = roomRepository.findByUser1AndUser2(user1, user2);
        if (!existingRoom.isPresent()) {
            existingRoom = roomRepository.findByUser1AndUser2(user2, user1);
        }
        if (existingRoom.isPresent()) {
            roomId = existingRoom.get().getRoomId();
            logger.info("There was existing roomId: {}", roomId);

            // 해당 roomId의 모든 RoomDetail 데이터를 조회
            List<RoomDetail> roomDetails = roomDetailRepository.findByRoomId(roomId);

            // 조회된 모든 메시지를 현재 세션에 전송
            for (RoomDetail roomDetail : roomDetails) {
                String messageToSend = roomDetail.getMessage();
                session.getBasicRemote().sendText(messageToSend);
                logger.info("채팅 기록 반환: {}", messageToSend);
            }
        } else {
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

        logger.info("Message from client in room [{}]: {}", roomId, message);

        for (Session s : sessionsInRoom) {
            s.getBasicRemote().sendText(message);
        }

        Date messageDate = new Date();

        RoomDetail roomDetail = new RoomDetail();
        roomDetail.setRoomId(roomId);
        roomDetail.setMessage(message);
        roomDetail.setMessageDate(messageDate);
        roomDetailRepository.save(roomDetail);

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