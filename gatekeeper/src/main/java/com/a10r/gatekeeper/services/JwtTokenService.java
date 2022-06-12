package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.configuration.JwtTokenUtil;
import com.a10r.gatekeeper.dao.JwtTokenRepository;
import com.a10r.gatekeeper.models.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtTokenService {

    transient JwtTokenUtil jwtTokenUtil;
    transient UserDetailsService userDetailsService;
    transient JwtTokenRepository jwtTokenRepository;

    @Autowired
    public JwtTokenService(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, JwtTokenRepository jwtTokenRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    public String getToken(String username) {
        Optional<JwtToken> jwtToken = jwtTokenRepository.findById(username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtToken.isPresent() && jwtTokenUtil.validateToken(jwtToken.get().getToken(), userDetails)) {
            return jwtToken.get().getToken();
        }
        String newToken = jwtTokenUtil.generateToken(userDetails);
        jwtTokenRepository.save(new JwtToken(username, newToken));
        return newToken;
    }
}
