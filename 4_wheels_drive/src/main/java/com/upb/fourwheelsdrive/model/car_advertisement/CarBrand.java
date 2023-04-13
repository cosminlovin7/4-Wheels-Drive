package com.upb.fourwheelsdrive.model.car_advertisement;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarBrand {
    @Id
    @GeneratedValue(
            generator = "car_brand_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "car_brand_sequence",
            sequenceName = "car_brand_sequence",
            allocationSize = 1
    )
    private Long id;
    private String brandName;
}
