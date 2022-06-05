package com.a10r.gatekeeper.configuration;

import com.a10r.gatekeeper.exceptions.MyBeanCreationException;
import com.a10r.gatekeeper.services.BcCryptWorkFactorService;
import com.a10r.gatekeeper.services.DatabaseUserDetailPasswordService;
import com.a10r.gatekeeper.services.WealthAppUserDetailsService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class SecurityConfiguration {

     transient JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
     transient JwtRequestFilter jwtRequestFilter;
     transient BcCryptWorkFactorService bcCryptWorkFactorService;
     transient DatabaseUserDetailPasswordService databaseUserDetailPasswordService;
     transient WealthAppUserDetailsService wealthAppUserDetailsService;

    @Autowired
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter,
                                 BcCryptWorkFactorService bcCryptWorkFactorService,
                                 DatabaseUserDetailPasswordService databaseUserDetailPasswordService,
                                 WealthAppUserDetailsService wealthAppUserDetailsService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.bcCryptWorkFactorService = bcCryptWorkFactorService;
        this.databaseUserDetailPasswordService = databaseUserDetailPasswordService;
        this.wealthAppUserDetailsService = wealthAppUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(bcCryptWorkFactorService.calculateStrength(), new SecureRandom()));
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsPasswordService(this.databaseUserDetailPasswordService);
        provider.setUserDetailsService(this.wealthAppUserDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new MyBeanCreationException();
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        try {
        httpSecurity
                // dont authenticate this particular request
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/authenticate/**").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//        httpSecurity.headers().frameOptions().sameOrigin();

            return httpSecurity.build();
        } catch (Exception e) {
            throw new MyBeanCreationException();
        }
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/api/authenticate/**");
    }
}
