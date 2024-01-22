package com.example.sign.repository;

import com.example.sign.model.SingleSignal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SingleSignalRepository extends JpaRepository<SingleSignal, Long> {

    boolean existsBySendUserAndReceiveUser(String sendUser, String receiveUser);

    @Transactional
    void deleteBySendUserAndReceiveUser(String sendUser, String receiveUser);

    List<SingleSignal> findByReceiveUser(String userId);

    List<SingleSignal> findBySendUser(String userId);
}