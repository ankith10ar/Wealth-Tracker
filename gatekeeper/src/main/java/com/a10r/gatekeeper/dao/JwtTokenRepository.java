package com.a10r.gatekeeper.dao;

import com.a10r.gatekeeper.models.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {

    List<JwtToken> findByUsername(@Param("username") String username);
}
