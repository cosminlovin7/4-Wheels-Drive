package com.upb.fourwheelsdrive.model;

import lombok.Data;

@Data
public class BaseResponse {
    private Integer status;
    private String message;

    public BaseResponse() {}
    public BaseResponse(final Integer status, final String message) {
        this.status = status;
        this.message = message;
    }
}
