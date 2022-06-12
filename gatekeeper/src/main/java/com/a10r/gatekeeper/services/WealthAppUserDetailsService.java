package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.models.WealthAppUser;
import com.a10r.gatekeeper.models.WealthAppUserDetails;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class WealthAppUserDetailsService implements UserDetailsService {

    transient UserService userService;

    @Autowired
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public WealthAppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WealthAppUser wealthAppUser = retrieveUserFromDB(username);
        return new WealthAppUserDetails(wealthAppUser);
    }

    private WealthAppUser retrieveUserFromDB(String username) {
        WealthAppUser wealthAppUser = userService.getUserFromUsername(username);
        if (wealthAppUser==null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return wealthAppUser;
    }

}
