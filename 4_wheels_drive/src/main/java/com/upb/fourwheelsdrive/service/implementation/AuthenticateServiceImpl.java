package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.model.authenticate.AuthenticateRequest;
import com.upb.fourwheelsdrive.service.AuthenticateService;
import com.upb.fourwheelsdrive.service.JwtService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    private final ApplicationUserServiceImpl applicationUserService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Override
    public String authenticate(AuthenticateRequest request) {
        try {
            authManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
            );
        } catch (DisabledException e) {
            log.error(Constants.ACCOUNT_NOT_ACTIVATED);
            throw new DisabledException(Constants.ACCOUNT_NOT_ACTIVATED);
        } catch (LockedException e) {
            log.error(Constants.ACCOUNT_LOCKED);
            throw new LockedException(Constants.ACCOUNT_LOCKED);
        } catch (BadCredentialsException e) {
            log.error(Constants.BAD_CREDENTIALS);
            throw new BadCredentialsException(Constants.BAD_CREDENTIALS);
        }

        UserDetails userDetails = applicationUserService.loadUserByUsername(request.getUsername());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", userDetails.getAuthorities());

        return jwtService.generateJwtToken(extraClaims, userDetails);
    }
}
