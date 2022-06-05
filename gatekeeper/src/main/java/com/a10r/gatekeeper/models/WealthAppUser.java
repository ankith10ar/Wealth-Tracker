package com.a10r.gatekeeper.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="wealthappuser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WealthAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @NotBlank
    String username;

    @NotBlank
    @Length(min = 5, max = 15)
    String password;

    @Email
    String emailId;

    public WealthAppUser(String username, String password, String emailId) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
    }
}
