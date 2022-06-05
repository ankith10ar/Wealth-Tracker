package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.dao.UserRepository;
import com.a10r.gatekeeper.models.WealthAppUser;
import com.a10r.gatekeeper.utils.UserDetailsMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

    transient UserRepository userRepository;
    transient UserDetailsMapper userDetailsMapper;

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
