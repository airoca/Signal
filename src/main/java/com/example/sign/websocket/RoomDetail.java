package com.example.sign.websocket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class RoomDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String roomId;
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;

}

