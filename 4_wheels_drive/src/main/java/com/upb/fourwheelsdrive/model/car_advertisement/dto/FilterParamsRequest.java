package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterParamsRequest {
    private Long brandId;
    private Long modelId;
    private Long generationId;
    private Integer minYear;
    private Integer maxYear;
    private Double minPrice;
    private Double maxPrice;
}
