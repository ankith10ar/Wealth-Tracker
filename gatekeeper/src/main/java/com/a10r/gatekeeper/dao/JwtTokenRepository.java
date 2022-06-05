package com.a10r.gatekeeper.dao;



import com.a10r.gatekeeper.models.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
}
