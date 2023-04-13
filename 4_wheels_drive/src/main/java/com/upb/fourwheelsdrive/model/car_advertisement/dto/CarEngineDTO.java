package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import lombok.Data;

@Data
public class CarEngineDTO {
    private String emissionLevelType;
    private Integer horsePower;
    private Double cylinderCapacity;
    private String transmissionType;
    private String drivetrain;
}
