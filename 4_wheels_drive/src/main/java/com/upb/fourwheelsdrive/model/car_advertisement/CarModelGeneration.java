package com.upb.fourwheelsdrive.model.car_advertisement;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarModelGeneration {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_model_generation_sequence"
    )
    @SequenceGenerator(
            name = "car_model_generation_sequence",
            sequenceName = "car_model_generation_sequence",
            allocationSize = 1
    )
    private Long id;
    private String generationName;
    @ManyToOne
    @JoinColumn(
            name = "model_id"
    )
    private CarModel carModel;
}
