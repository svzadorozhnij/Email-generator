package com.example.Email.generator.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int log_id;

    @Column(nullable = false)
    private int user_id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Enum type;

    private Timestamp createdOn;

    public Log() {
    }

    public Log(int user_id, Enum type, Timestamp createdOn) {
        this.user_id = user_id;
        this.type = type;
        this.createdOn = createdOn;
    }
}
