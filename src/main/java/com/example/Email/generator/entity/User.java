package com.example.Email.generator.entity;

import com.sun.istack.NotNull;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String  email;

    private Date createdOn;

    public User() {
    }

    public User(String username, String email, Date createdOn) {
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
