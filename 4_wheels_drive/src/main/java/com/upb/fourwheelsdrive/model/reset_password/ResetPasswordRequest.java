package com.upb.fourwheelsdrive.model.reset_password;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @JsonProperty("password")
    private String password;
}
