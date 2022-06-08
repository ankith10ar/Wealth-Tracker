package com.a10r.gatekeeper.controllers;


import com.a10r.gatekeeper.configuration.JwtTokenUtil;
import com.a10r.gatekeeper.exceptions.WealthUserDisabledException;
import com.a10r.gatekeeper.exceptions.WealthUserInvalidCredentialException;
import com.a10r.gatekeeper.models.CreateUserRequest;
import com.a10r.gatekeeper.models.JwtRequest;
import com.a10r.gatekeeper.models.JwtResponse;
import com.a10r.gatekeeper.models.WealthAppUser;
import com.a10r.gatekeeper.services.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/api/authenticate")
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class UserAuthenticationController {

    transient AuthenticationManager authenticationManager;
    transient JwtTokenUtil jwtTokenUtil;
    transient UserDetailsService jwtInMemoryUserDetailsService;
    transient UserService userService;
    transient PasswordEncoder passwordEncoder;

    @Autowired
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public UserAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                        UserDetailsService jwtInMemoryUserDetailsService, UserService userService,
                                        PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {

        WealthAppUser wealthAppUser = WealthAppUser.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .emailId(createUserRequest.getEmailId()).build();

        userService.addUser(wealthAppUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created Successfully");
    }

    @SuppressWarnings("PMD.PreserveStackTrace")
    //TODO: Add the stack to log debug
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new WealthUserDisabledException();
        } catch (BadCredentialsException e) {
            throw new WealthUserInvalidCredentialException();
        }
    }
}
