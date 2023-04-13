package com.upb.fourwheelsdrive.model.car_advertisement;

import com.upb.fourwheelsdrive.model.car_advertisement.enums.BodyType;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.ColorType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarBody {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_body_sequence"
    )
    @SequenceGenerator(
            name = "car_body_sequence",
            sequenceName = "car_body_sequence",
            allocationSize = 1
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Enumerated(EnumType.STRING)
    private ColorType colorType;
    private Integer nrOfSeats;
    @OneToOne
    @JoinColumn(
            name = "car_advert_id"
    )
    private CarAdvert carAdvert;
}
