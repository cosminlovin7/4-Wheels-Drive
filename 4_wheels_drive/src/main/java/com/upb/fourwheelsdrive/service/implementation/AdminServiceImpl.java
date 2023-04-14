package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.admin_control.BrandNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelGenerationNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelNamesDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import com.upb.fourwheelsdrive.repository.CarBrandRepository;
import com.upb.fourwheelsdrive.repository.CarModelGenerationRepository;
import com.upb.fourwheelsdrive.repository.CarModelRepository;
import com.upb.fourwheelsdrive.service.AdminService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;
    private CarModelGenerationRepository carModelGenerationRepository;

    @Override
    public BrandNamesDTO getBrands() {
        BrandNamesDTO brandNamesDTO = new BrandNamesDTO();

        brandNamesDTO.setBrands(carBrandRepository.findAll());

        return brandNamesDTO;
    }

    @Override
    public ModelNamesDTO getModels(String brandName) {
        ModelNamesDTO modelNamesDTO = new ModelNamesDTO();

        CarBrand carBrand = carBrandRepository.findCarBrandByBrandName(brandName).orElseThrow(() ->
                new BaseException(Constants.INVALID_CAR_BRAND, HttpStatus.BAD_REQUEST));

        modelNamesDTO.setBrand(carBrand);
        modelNamesDTO.setModels(carModelRepository.findAllByCarBrand(carBrand));

        return modelNamesDTO;
    }

    @Override
    public ModelGenerationNamesDTO getModelGenerations(String brandName, String modelName) {
        ModelGenerationNamesDTO modelGenerationNamesDTO = new ModelGenerationNamesDTO();

        CarBrand carBrand = carBrandRepository.findCarBrandByBrandName(brandName).orElseThrow(() ->
                new BaseException(Constants.INVALID_CAR_BRAND, HttpStatus.BAD_REQUEST));

        CarModel carModel= carModelRepository.findCarModelByCarBrandAndModelName(carBrand, modelName).orElseThrow(() ->
                new BaseException(Constants.INVALID_CAR_MODEL, HttpStatus.BAD_REQUEST));

        modelGenerationNamesDTO.setBrand(carBrand);
        modelGenerationNamesDTO.setModel(carModel);
        modelGenerationNamesDTO.setGenerations(carModelGenerationRepository.findAllByCarModel(carModel));

        return modelGenerationNamesDTO;
    }
}
