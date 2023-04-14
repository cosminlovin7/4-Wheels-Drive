package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.car_advertisement.CarModel;
import com.upb.fourwheelsdrive.model.car_advertisement.CarModelGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModelGenerationRepository extends JpaRepository<CarModelGeneration, Long> {
    Optional<CarModelGeneration> findCarModelGenerationByGenerationName(final String generationName);
    List<CarModelGeneration> findAllByCarModel(CarModel carModel);
}
