package com.chatapp.models;

import lombok.Data;

import java.time.Instant;
import javax.persistence.*;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
