package com.example.sign.model;

import jakarta.persistence.Column;
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

    @Column(name = "user1_x_coordinate")
    private Float user1XCoordinate;
    @Column(name = "user1_y_coordinate")
    private Float user1YCoordinate;
    @Column(name = "user1_z_coordinate")
    private Float user1ZCoordinate;

    @Column(name = "user2_x_coordinate")
    private Float user2XCoordinate;
    @Column(name = "user2_y_coordinate")
    private Float user2YCoordinate;
    @Column(name = "user2_z_coordinate")
    private Float user2ZCoordinate;

}

