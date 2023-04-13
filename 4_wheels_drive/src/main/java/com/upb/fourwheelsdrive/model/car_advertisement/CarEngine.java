package com.upb.fourwheelsdrive.model.car_advertisement;

import com.upb.fourwheelsdrive.model.car_advertisement.enums.DrivetrainType;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.EmissionLevelType;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarEngine {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_engine_sequence"
    )
    @SequenceGenerator(
            name = "car_engine_sequence",
            sequenceName = "car_engine_sequence",
            allocationSize = 1
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private EmissionLevelType emissionLevelType;
    private Integer horsePower;
    private Double cylinderCapacity;
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    @Enumerated(EnumType.STRING)
    private DrivetrainType drivetrain;
    @OneToOne
    @JoinColumn(
            name = "car_advert_id"
    )
    private CarAdvert carAdvert;
}
