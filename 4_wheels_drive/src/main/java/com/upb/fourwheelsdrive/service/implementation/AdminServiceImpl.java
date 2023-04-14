package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.admin_control.*;
import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModelGeneration;
import com.upb.fourwheelsdrive.repository.CarBrandRepository;
import com.upb.fourwheelsdrive.repository.CarModelGenerationRepository;
import com.upb.fourwheelsdrive.repository.CarModelRepository;
import com.upb.fourwheelsdrive.service.AdminService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void addBrand(RequestBrandDTO requestBrandDTO) {
        Optional<CarBrand> carBrand = carBrandRepository.findCarBrandByBrandName(requestBrandDTO.getBrandName());

        if (carBrand.isPresent()) {
            throw new BaseException(Constants.BRAND_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        CarBrand newCarBrand = new CarBrand();
        newCarBrand.setBrandName(requestBrandDTO.getBrandName());

        carBrandRepository.save(newCarBrand);
    }

    @Override
    public void addModel(RequestModelDTO requestModelDTO) {
        Optional<CarBrand> carBrand = carBrandRepository.findCarBrandByBrandName(requestModelDTO.getBrandName());

        if (carBrand.isEmpty()) {
            throw new BaseException(Constants.INVALID_CAR_BRAND, HttpStatus.BAD_REQUEST);
        }

        Optional<CarModel> carModel = carModelRepository.findCarModelByModelName(requestModelDTO.getModelName());

        if (carModel.isPresent()) {
            throw new BaseException(Constants.MODEL_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        CarModel newCarModel = new CarModel();
        newCarModel.setCarBrand(carBrand.get());
        newCarModel.setModelName(requestModelDTO.getModelName());

        carModelRepository.save(newCarModel);
    }

    @Override
    public void addModelGeneration(RequestModelGenerationDTO requestModelGenerationDTO) {
        Optional<CarModel> carModel = carModelRepository.findCarModelByModelName(requestModelGenerationDTO.getModelName());

        if (carModel.isEmpty()) {
            throw new BaseException(Constants.INVALID_CAR_MODEL, HttpStatus.BAD_REQUEST);
        }

        Optional<CarModelGeneration> carModelGeneration = carModelGenerationRepository
                .findCarModelGenerationByGenerationName(requestModelGenerationDTO.getGenerationName());

        if (carModelGeneration.isPresent()) {
            throw new BaseException(Constants.GENERATION_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        CarModelGeneration newCarModelGeneration = new CarModelGeneration();
        newCarModelGeneration.setCarModel(carModel.get());
        newCarModelGeneration.setGenerationName(requestModelGenerationDTO.getGenerationName());

        carModelGenerationRepository.save(newCarModelGeneration);
    }
}
