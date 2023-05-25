package com.chatapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "contact_id")
    private int contactId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

//    @ManyToOne
//    @MapsId("userId")
//    private UserAccount user;
//
//    @ManyToOne
//    @MapsId("contactId")
//    private UserAccount contact;

    public Contact(int userId, int contactId, String displayName) {
        this.userId = userId;
        this.contactId = contactId;
        this.displayName = displayName;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.isDeleted = false;
    }
}
