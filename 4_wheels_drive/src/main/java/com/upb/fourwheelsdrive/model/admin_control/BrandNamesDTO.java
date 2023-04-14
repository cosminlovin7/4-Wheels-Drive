package com.upb.fourwheelsdrive.model.admin_control;

import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import lombok.Data;

import java.util.List;

@Data
public class BrandNamesDTO {
    private List<CarBrand> brands;
}
