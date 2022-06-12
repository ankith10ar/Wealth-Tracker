package com.a10r.gatekeeper.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;

@Getter
public class WealthAppUserDetails extends User {

    private final Long id;

    public WealthAppUserDetails(WealthAppUser wealthAppUser) {
        super(wealthAppUser.getUsername(), wealthAppUser.getPassword(),
                new ArrayList<GrantedAuthority>(Collections.singletonList(new SimpleGrantedAuthority("user"))));
        this.id = wealthAppUser.getUserId();
    }
}
