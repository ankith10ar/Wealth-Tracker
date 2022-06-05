package com.a10r.gatekeeper.utils;

import com.a10r.gatekeeper.models.WealthAppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(WealthAppUser wealthAppUser) {
        return User.withUsername(wealthAppUser.getUsername())
                .password(wealthAppUser.getPassword())
                .roles("USER")
                .build();
    }
}
