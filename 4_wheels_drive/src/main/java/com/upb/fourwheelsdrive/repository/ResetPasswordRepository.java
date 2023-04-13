package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken, Long> {
    @Query("SELECT e FROM ResetPasswordToken e WHERE e.applicationUser.id = :userId")
    Optional<ResetPasswordToken> findByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM ResetPasswordToken e WHERE e.token = :token AND e.applicationUser.id = :userId")
    Optional<ResetPasswordToken> findByTokenAndUserId(String token, @Param("userId") Long userId);
}
