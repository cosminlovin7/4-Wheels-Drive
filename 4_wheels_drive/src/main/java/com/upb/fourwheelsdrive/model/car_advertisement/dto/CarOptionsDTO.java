package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import lombok.Data;

@Data
public class CarOptionsDTO {
    private boolean hasAppleCarPlay;
    private boolean hasHeadUpDisplay;
    private boolean hasAndroid;
    private boolean hasBluetooth;
    private boolean hasAirConditioning;
    private boolean hasDualClimatic;
    private boolean hasPanoramicRoof;
    private boolean hasSunRoof;
    private String upholsteryType;
    private boolean hasElectricDriverSeat;
    private boolean hasElectricPassengerSeat;
    private boolean hasElectricSeats;
    private boolean hasHeatedDriverSeat;
    private boolean hasHeatedPassengerSeat;
    private boolean hasHeatedSteeringWheel;
    private boolean hasHeatedWindscreen;
    private boolean hasCruiseControl;
    private boolean hasDistanceControl;
    private boolean has360Cameras;
    private boolean hasParkingSensors;
    private String headlightsType;
}
