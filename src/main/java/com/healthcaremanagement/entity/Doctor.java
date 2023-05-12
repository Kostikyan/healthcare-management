package com.healthcaremanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="doctor")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String specialty;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="profile_pic")
    private String profilePic;
}
