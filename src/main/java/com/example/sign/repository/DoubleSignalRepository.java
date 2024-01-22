package com.example.sign.repository;

import com.example.sign.model.DoubleSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleSignalRepository extends JpaRepository<DoubleSignal, Long> {
    boolean existsByUser1AndUser2(String senderId, String receiverId);

    void deleteByUser1AndUser2(String senderId, String receiverId);
}
