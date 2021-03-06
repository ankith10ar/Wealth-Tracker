package com.a10r.gatekeeper.models;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="wealthappuser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Transactional
public class WealthAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @NotBlank
    String username;

    @NotBlank
    String password;

    @Email
    String emailId;

}
