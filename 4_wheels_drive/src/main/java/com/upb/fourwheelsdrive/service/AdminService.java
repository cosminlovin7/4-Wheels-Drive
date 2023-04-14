package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.admin_control.BrandNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelGenerationNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelNamesDTO;

public interface AdminService {
    BrandNamesDTO getBrands();
    ModelNamesDTO getModels(String brandName);
    ModelGenerationNamesDTO getModelGenerations(String brandName, String modelName);
}
