package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository //good practice
@Transactional(readOnly = true)
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(final String email);
    Optional<ApplicationUser> findByUsername(final String username);
}
