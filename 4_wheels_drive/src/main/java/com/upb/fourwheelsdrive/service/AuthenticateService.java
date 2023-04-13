package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.authenticate.AuthenticateRequest;

public interface AuthenticateService {

    String authenticate(AuthenticateRequest request);
}
