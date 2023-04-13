package com.upb.fourwheelsdrive.model.authenticate;

import com.upb.fourwheelsdrive.model.BaseResponse;
import lombok.Data;

@Data
public class AuthenticateResponse extends BaseResponse {
    private String token;

    public AuthenticateResponse() {}
    public AuthenticateResponse(final Integer status, final String message, final String token) {
        super(status, message);
        this.token = token;
    }

}
