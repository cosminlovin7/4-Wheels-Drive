package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.register.RegisterActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterActivationRepository extends JpaRepository<RegisterActivationToken, Long> {
    Optional<RegisterActivationToken> findByToken(final String token);
    @Query("SELECT e FROM RegisterActivationToken e WHERE e.token = :token AND e.applicationUser.id = :userId")
    Optional<RegisterActivationToken> findByTokenAndUserId(@Param("token") String token, @Param("userId") Long userId);
    @Query("SELECT e FROM RegisterActivationToken e WHERE e.applicationUser.id = :userId")
    Optional<RegisterActivationToken> findByUserId(@Param("userId") Long userId);
}
