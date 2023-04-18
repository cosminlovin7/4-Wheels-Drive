package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterParamsRequest {
    private Long brandId;
    private Long modelId;
    private Long generationId;
    private Integer year;
    private Integer minYear;
    private Integer maxYear;
    private Double price;
    private Double minPrice;
    private Double maxPrice;
    private String fuelType;
    private String country;
}
