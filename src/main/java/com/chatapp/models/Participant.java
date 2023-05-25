package com.chatapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "conversation_id")
    private int conversationId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role")
    private EParticipantRole role;

    @Column(name = "is_removed")
    private Boolean isRemoved;

    public Participant(int conversationId, int userId, EParticipantRole role) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.role = role;
        this.isRemoved = false;
    }
}
