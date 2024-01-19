package com.example.sign.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String password;

    private String name;

    private String photo;

    private Date birth;

    private String region;

    private String gender;

    private String introduction;

    //위치
    private Float x_coordinate;
    private Float y_coordinate;
    private Float z_coordinate;
}
