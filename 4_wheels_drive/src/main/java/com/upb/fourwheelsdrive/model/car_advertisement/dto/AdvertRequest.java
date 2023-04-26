package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class AdvertRequest {
    @NonNull
    private String brand;
    @NonNull
    private Double price;
    @NonNull
    private Integer year;
    @NonNull
    private String fuelType;
    private Integer km;
    private String country;
    @NonNull
    private String modelName;
    @NonNull
    private String generationName;

    @JsonProperty("body")
    private CarBodyDTO carBodyDTO;
    @JsonProperty("engine")
    private CarEngineDTO carEngineDTO;
    @JsonProperty("options")
    private CarOptionsDTO carOptionsDTO;

    private String description;
    @JsonProperty("photos")
    private List<String> photosData;
}
