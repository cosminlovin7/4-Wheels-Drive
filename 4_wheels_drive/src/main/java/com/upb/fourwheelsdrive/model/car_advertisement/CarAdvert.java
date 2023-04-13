package com.upb.fourwheelsdrive.model.car_advertisement;

import com.upb.fourwheelsdrive.model.car_advertisement.enums.FuelType;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarAdvert {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_advert_sequence"
    )
    @SequenceGenerator(
            name = "car_advert_sequence",
            sequenceName = "car_advert_sequence",
            allocationSize = 1
    )
    private Long id;
    private Double price;
    private Integer year;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private String country;
    private String description;
    @OneToOne()
    @JoinColumn(
            name = "car_brand_id"
    )
    private CarBrand carBrand;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(
            name = "car_body_id"
    )
    private CarBody carBody;
    @OneToOne
    @JoinColumn(
            name = "car_model_id"
    )
    private CarModel carModel;
    @OneToOne
    @JoinColumn(
            name = "car_generation_id"
    )
    private CarModelGeneration carModelGeneration;
    @OneToOne
    @JoinColumn(
            name = "car_engine_id"
    )
    private CarEngine carEngine;
    @OneToOne
    @JoinColumn(
            name = "car_options_id"
    )
    private CarOptions carOptions;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private ApplicationUser applicationUser;
}
