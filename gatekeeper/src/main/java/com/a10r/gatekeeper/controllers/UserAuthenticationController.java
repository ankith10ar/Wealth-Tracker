package com.a10r.gatekeeper.controllers;


import com.a10r.gatekeeper.exceptions.WealthUserDisabledException;
import com.a10r.gatekeeper.exceptions.WealthUserInvalidCredentialException;
import com.a10r.gatekeeper.models.CreateUserRequest;
import com.a10r.gatekeeper.models.JwtRequest;
import com.a10r.gatekeeper.models.JwtResponse;
import com.a10r.gatekeeper.models.WealthAppUser;
import com.a10r.gatekeeper.services.JwtTokenService;
import com.a10r.gatekeeper.services.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/api/authenticate")
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class UserAuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(UserAuthenticationController.class);

    transient AuthenticationManager authenticationManager;
    transient UserService userService;
    transient PasswordEncoder passwordEncoder;
    transient JwtTokenService jwtTokenService;

    @Autowired
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public UserAuthenticationController(AuthenticationManager authenticationManager,UserService userService,
                                        PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        String token = jwtTokenService.getToken(authenticationRequest.getUsername());

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
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            LOG.error("user disabled exception", e);
            throw new WealthUserDisabledException();
        } catch (BadCredentialsException e) {
            LOG.error("bad credentials exception", e);
            throw new WealthUserInvalidCredentialException();
        }
    }
}
