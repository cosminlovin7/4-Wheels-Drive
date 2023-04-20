package com.upb.fourwheelsdrive.model.admin_control;

import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import lombok.Data;

import java.util.List;

@Data
public class ModelNamesDTO {
    private CarBrand brand;
    private List<CarModelDTO> models;
}
