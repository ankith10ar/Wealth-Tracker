package com.a10r.gatekeeper.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {


    @NotBlank
    String username;

    @NotBlank
    @Length(min = 5, max = 15)
    String password;

    @Email
    String emailId;
}
