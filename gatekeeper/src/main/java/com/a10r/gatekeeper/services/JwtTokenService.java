package com.a10r.gatekeeper.services;

import com.a10r.gatekeeper.configuration.JwtTokenUtil;
import com.a10r.gatekeeper.dao.JwtTokenRepository;
import com.a10r.gatekeeper.models.JwtToken;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
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
        Collection<JwtToken> jwtTokens = jwtTokenRepository.findByUsername(username);
        Optional<JwtToken> jwtToken = chooseLatestToken(jwtTokens);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtToken.isPresent() && jwtTokenUtil.validateToken(jwtToken.get().getToken(), userDetails)) {
            return jwtToken.get().getToken();
        }
        String newToken = jwtTokenUtil.generateToken(userDetails);
        jwtTokenRepository.save(new JwtToken(username, newToken));
        return newToken;
    }

    public Optional<String> generateTokenIfCloseToExpiry(String jwtToken, UserDetails userDetails) {
        if (jwtTokenUtil.isTokenCloseToExpiry(jwtToken)) {
            String newJwtToken = jwtTokenUtil.generateToken(userDetails);
            jwtTokenRepository.save(new JwtToken(userDetails.getUsername(), newJwtToken));
            return Optional.of(newJwtToken);
        }
        return Optional.empty();
    }

    private Optional<JwtToken> chooseLatestToken(Collection<JwtToken> jwtTokens) {
        if (Collections.isEmpty(jwtTokens)) {
            return Optional.empty();
        }
        return jwtTokens.stream().max(Comparator.comparing(token -> jwtTokenUtil.getExpirationDateFromToken(token.getToken()).toInstant()));
    }
}
