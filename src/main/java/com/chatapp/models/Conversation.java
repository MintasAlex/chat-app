package com.chatapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Conversation(String title) {
        this.title = title;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
