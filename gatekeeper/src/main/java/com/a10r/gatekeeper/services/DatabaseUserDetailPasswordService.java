package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.dao.UserRepository;
import com.a10r.gatekeeper.models.WealthAppUser;
import com.a10r.gatekeeper.utils.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Autowired
    public DatabaseUserDetailPasswordService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        WealthAppUser wealthAppUser = userRepository.getUserByUsername(user.getUsername());
        wealthAppUser.setPassword(newPassword);
        return userDetailsMapper.toUserDetails(wealthAppUser);
    }
}
