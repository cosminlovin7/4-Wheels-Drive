package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.admin_control.*;

public interface AdminService {
    BrandNamesDTO getBrands();
    ModelNamesDTO getModels(String brandName);
    ModelGenerationNamesDTO getModelGenerations(String brandName, String modelName);
    void addBrand(RequestBrandDTO requestBrandDTO);
    void addModel(RequestModelDTO requestModelDTO);
    void addModelGeneration(RequestModelGenerationDTO requestModelGenerationDTO);
}
