package com.upb.fourwheelsdrive.model.car_advertisement;

import com.upb.fourwheelsdrive.model.car_advertisement.enums.HeadlightsType;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.UpholsteryType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarOptions {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_options_sequence"
    )
    @SequenceGenerator(
            name = "car_options_sequence",
            sequenceName = "car_options_sequence",
            allocationSize = 1
    )
    private Long id;
    private boolean hasAppleCarPlay;
    private boolean hasHeadUpDisplay;
    private boolean hasAndroid;
    private boolean hasBluetooth;
    private boolean hasAirConditioning;
    @Column(name = "has_dual_climatronic")
    private boolean hasDualClimatic;
    private boolean hasPanoramicRoof;
    private boolean hasSunRoof;
    @Enumerated(EnumType.STRING)
    private UpholsteryType upholsteryType;
    private boolean hasElectricDriverSeat;
    private boolean hasElectricPassengerSeat;
    private boolean hasElectricSeats;
    private boolean hasHeatedDriverSeat;
    private boolean hasHeatedPassengerSeat;
    private boolean hasHeatedSteeringWheel;
    private boolean hasHeatedWindscreen;
    private boolean hasCruiseControl;
    private boolean hasDistanceControl;
    @Column(name = "has_360_cameras")
    private boolean has360Cameras;
    private boolean hasParkingSensors;
    @Enumerated(EnumType.STRING)
    private HeadlightsType headlightsType;
    @OneToOne
    @JoinColumn(
            name = "car_advert_id"
    )
    private CarAdvert carAdvert;
}
