package com.a10r.gatekeeper.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BcryptWorkFactor {

    private int strength;
    private long duration;
}
