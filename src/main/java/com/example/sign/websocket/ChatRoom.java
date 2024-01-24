package com.example.sign.websocket;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    private String roomId;
    private String user1;
    private String user2;
    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

    //hibernate 사용을 위한 생성자
    public ChatRoom() {
    }

    @Builder
    public ChatRoom(String roomId, String user1, String user2) {
        this.roomId = roomId;
        this.user1 = user1;
        this.user2 = user2;
    }
}
