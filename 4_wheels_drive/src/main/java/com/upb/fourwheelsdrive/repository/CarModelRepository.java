package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.car_advertisement.CarBrand;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> findCarModelByModelName(final String modelName);
    List<CarModel> findAllByCarBrand(CarBrand carBrand);
    Optional<CarModel> findCarModelByCarBrandAndModelName(CarBrand carBrand, String modelName);
}
