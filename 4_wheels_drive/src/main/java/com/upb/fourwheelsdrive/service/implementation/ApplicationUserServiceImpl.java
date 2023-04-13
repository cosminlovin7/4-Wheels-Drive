package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.register.RegisterActivationToken;
import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordToken;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.repository.ApplicationUserRepository;
import com.upb.fourwheelsdrive.service.RegisterActivationService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements UserDetailsService {

    @Value("${registration_service.token_active_time}")
    private String tokenActiveTime;
    private final ApplicationUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegisterActivationService registerActivationService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws BaseException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new BaseException(Constants.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    // this way we make sure that the whole method is marked as a transaction (all or nothing)
    //so if a transaction fails, then the others will be rolled back.
    @Transactional
    public String registerUser(final ApplicationUser applicationUser) {
        boolean userAlreadyRegistered = userRepository.findByUsername(applicationUser.getUsername()).isPresent();

        if (userAlreadyRegistered) {
            throw new BaseException(Constants.USERNAME_ALREADY_REGISTERED, HttpStatus.CONFLICT);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encodedPassword);

        userRepository.save(applicationUser);

        RegisterActivationToken registerActivationToken = generateRegisterToken(applicationUser);
        registerActivationService.saveActivationToken(registerActivationToken);

        return registerActivationToken.getToken();
    }

    public RegisterActivationToken generateRegisterToken(final ApplicationUser applicationUser) {
        UUID token = UUID.randomUUID();

        return new RegisterActivationToken(
                token.toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plus(Duration.parse(tokenActiveTime)),
                applicationUser
        );
    }

    public ResetPasswordToken generateResetPasswordToken(final ApplicationUser applicationUser) {
        UUID token = UUID.randomUUID();

        return new ResetPasswordToken(
                token.toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plus(Duration.parse(tokenActiveTime)),
                applicationUser
        );
    }

    public void saveUser(final ApplicationUser applicationUser) {
        userRepository.save(applicationUser);
    }
}
