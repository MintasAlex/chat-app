package com.chatapp.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    private String password;

}

