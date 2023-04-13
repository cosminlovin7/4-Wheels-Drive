package com.upb.fourwheelsdrive.model.car_advertisement;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_model_sequence"
    )
    @SequenceGenerator(
            name = "car_model_sequence",
            sequenceName = "car_model_sequence",
            allocationSize = 1
    )
    private Long id;
    private String modelName;
    @ManyToOne
    @JoinColumn(
        name = "brand_id"
    )
    private CarBrand carBrand;
}
