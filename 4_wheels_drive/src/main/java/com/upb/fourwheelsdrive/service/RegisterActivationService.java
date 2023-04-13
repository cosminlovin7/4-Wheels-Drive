package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.register.RegisterActivationToken;

import java.util.Optional;

public interface RegisterActivationService {

    void saveActivationToken(final RegisterActivationToken registerActivationToken);
    Optional<RegisterActivationToken> getRegisterActivationToken(final String token);
    RegisterActivationToken getTokenByUserId(final Long userId);
    void activateAccount(final String username, final String token);
}
