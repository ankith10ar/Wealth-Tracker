package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.dao.UserRepository;
import com.a10r.gatekeeper.models.WealthAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean addUser(WealthAppUser wealthAppUser) {
        if (userRepository.getUserByUsername(wealthAppUser.getUsername())==null) {
            userRepository.save(wealthAppUser);
            return true;
        }
        return false;
    }

    public WealthAppUser getUserFromUsername(String username) {
        return userRepository.getUserByUsername(username);
    }



}