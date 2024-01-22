package com.example.sign.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(DoubleSignalId.class)
@Getter
@Setter
public class DoubleSignal {

    @Id
    private String user1;
    @Id
    private String user2;

}

