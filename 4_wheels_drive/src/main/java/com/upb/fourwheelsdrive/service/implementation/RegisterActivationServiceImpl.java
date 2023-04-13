package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.register.RegisterActivationToken;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.repository.ApplicationUserRepository;
import com.upb.fourwheelsdrive.repository.RegisterActivationRepository;
import com.upb.fourwheelsdrive.service.RegisterActivationService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegisterActivationServiceImpl implements RegisterActivationService {

    private final RegisterActivationRepository registerActivationRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public void saveActivationToken(final RegisterActivationToken registerActivationToken) {
        registerActivationRepository.save(registerActivationToken);
    }

    @Override
    public Optional<RegisterActivationToken> getRegisterActivationToken(final String token) {
        return registerActivationRepository.findByToken(token);
    }

    @Override
    public RegisterActivationToken getTokenByUserId(final Long userId) {
        return registerActivationRepository
                .findByUserId(userId)
                .orElseThrow(() -> new BaseException(Constants.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    @Transactional
    public void activateAccount(final String username, final String token) {
        ApplicationUser applicationUser = applicationUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new BaseException(Constants.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST));

        RegisterActivationToken registerActivationToken = registerActivationRepository
                .findByTokenAndUserId(token, applicationUser.getId())
                .orElseThrow(() -> new BaseException(Constants.INVALID_REGISTRATION_TOKEN, HttpStatus.BAD_REQUEST));

        registerActivationToken.setActivated(true);
        applicationUser.setEnabled();

        registerActivationRepository.save(registerActivationToken);
        applicationUserRepository.save(applicationUser);

    }
}
