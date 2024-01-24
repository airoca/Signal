package com.example.sign.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<ChatRoom, String> {
}

