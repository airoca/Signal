package com.example.sign.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    List<RoomDetail> findByRoomId(String roomId);
}
