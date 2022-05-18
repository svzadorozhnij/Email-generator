package com.example.Email.generator.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String expression;
    @Column(nullable = false)
    private Timestamp createdOn;

    public Cron() {
    }

    public Cron(String expression, Timestamp createdOn) {
        this.expression = expression;
        this.createdOn = createdOn;
    }
}
