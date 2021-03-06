package com.example.Email.generator.entity;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String  email;
    private Timestamp createdOn;


    public User() {
    }

    public User(String username, String email, Timestamp createdOn) {
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
    }

}
