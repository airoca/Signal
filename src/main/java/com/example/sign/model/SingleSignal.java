package com.example.sign.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(SingleSignalId.class)
@Getter
@Setter
public class SingleSignal {

    @Id
    private String sendUser;
    @Id
    private String receiveUser;

}

