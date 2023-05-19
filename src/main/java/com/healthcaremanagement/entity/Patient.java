package com.healthcaremanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;

    @Column(name="date_of_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirthday;

    @SneakyThrows
    public String getDateOfBirthday(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dateOfBirthday);
    }
}
