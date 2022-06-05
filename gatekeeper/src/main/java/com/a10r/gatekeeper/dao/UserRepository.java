package com.a10r.gatekeeper.dao;

import com.a10r.gatekeeper.models.WealthAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<WealthAppUser, Long> {

    @Query("SELECT u FROM WealthAppUser u WHERE u.username = :username and u.password = :password")
    WealthAppUser getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u FROM WealthAppUser u WHERE u.username = :username")
    WealthAppUser getUserByUsername(@Param("username") String username);
}
