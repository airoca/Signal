package com.example.sign.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByUser1AndUser2(String user1, String user2);
}


