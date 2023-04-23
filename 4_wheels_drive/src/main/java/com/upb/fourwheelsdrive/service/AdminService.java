package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.admin_control.*;

import java.util.List;

public interface AdminService {
    BrandNamesDTO getBrands();
    ModelNamesDTO getModels(String brandName);
    List<CarModelDTO> getAllModels();
    ModelGenerationNamesDTO getModelGenerations(String brandName, String modelName);
    List<CarGenerationDTO> getAllGenerations();
    void addBrand(RequestBrandDTO requestBrandDTO);
    void addModel(RequestModelDTO requestModelDTO);
    void addModelGeneration(RequestModelGenerationDTO requestModelGenerationDTO);
}
