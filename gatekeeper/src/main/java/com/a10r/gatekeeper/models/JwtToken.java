package com.a10r.gatekeeper.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;


@RedisHash("JwtToken")
@Data
@AllArgsConstructor
@Getter
public class JwtToken {

    @Column(name="username", length=100)
    @Indexed
    private String username;
    @Id
    private String token;
}
