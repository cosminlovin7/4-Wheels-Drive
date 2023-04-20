package com.upb.fourwheelsdrive.model.admin_control;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModelGeneration;
import lombok.Data;

import java.util.List;

@Data
public class ModelGenerationNamesDTO {
    private CarBrand brand;
    @JsonProperty("model")
    private CarModelDTO model;
    private List<CarGenerationDTO> generations;
}
