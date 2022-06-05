package com.a10r.gatekeeper.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("JwtToken")
@Data
public class JwtToken {

    long userId;
    @Id
    String jwtToken;
}
