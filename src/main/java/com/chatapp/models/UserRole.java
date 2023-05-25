package com.chatapp.models;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "user_role")
@Data
@IdClass(UserRoleId.class)
@NoArgsConstructor
public class UserRole {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;


}
