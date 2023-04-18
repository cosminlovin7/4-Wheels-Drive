package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.FuelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CarAdvertDTO {
    private Long id;
    private String brand;
    private String model;
    private String generation;
    private Double price;
    private Integer year;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private String country;
    private String description;

    @JsonProperty("body")
    private CarBodyDTO carBodyDTO;
    @JsonProperty("engine")
    private CarEngineDTO carEngineDTO;
    @JsonProperty("options")
    private CarOptionsDTO carOptionsDTO;
}
