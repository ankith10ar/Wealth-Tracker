package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.models.WealthAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Transactional
public class WealthAppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WealthAppUser wealthAppUser = userService.getUserFromUsername(username);
        if (wealthAppUser!=null) {
            return new User(wealthAppUser.getUsername(), wealthAppUser.getPassword(),
                    new ArrayList<GrantedAuthority>(Collections.singletonList(new SimpleGrantedAuthority("user"))));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
