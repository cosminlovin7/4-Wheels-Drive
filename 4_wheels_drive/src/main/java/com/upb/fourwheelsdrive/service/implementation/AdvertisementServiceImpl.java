package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.car_advertisement.*;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarBodyDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarEngineDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarOptionsDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.*;
import com.upb.fourwheelsdrive.repository.*;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private CarAdvertRepository carAdvertRepository;
    private CarBodyRepository carBodyRepository;
    private CarEngineRepository carEngineRepository;
    private CarOptionsRepository carOptionsRepository;
    private CarModelRepository carModelRepository;
    private CarModelGenerationRepository carModelGenerationRepository;
    private CarBrandRepository carBrandRepository;

    @Transactional
    @Override
    public void createCarAdvert(AdvertRequest advertRequest) {
        CarAdvert carAdvert = new CarAdvert();
        CarBody carBody = new CarBody();
        CarEngine carEngine = new CarEngine();
        CarOptions carOptions = new CarOptions();

        CarBrand carBrand = carBrandRepository.findCarBrandByBrandName(
                advertRequest.getBrand()).orElseThrow(() ->
                    new BaseException(Constants.INVALID_CAR_BRAND, HttpStatus.BAD_REQUEST)
        );

        CarModel carModel = carModelRepository.findCarModelByModelName(
                advertRequest.getModelName()).orElseThrow(() ->
                    new BaseException(Constants.INVALID_CAR_MODEL, HttpStatus.BAD_REQUEST)
        );

        CarModelGeneration carModelGeneration = carModelGenerationRepository.findCarModelGenerationByGenerationName(
                advertRequest.getGenerationName()).orElseThrow(() ->
                    new BaseException(Constants.INVALID_CAR_MODEL_GENERATION, HttpStatus.BAD_REQUEST)
        );

        carAdvert.setCarBrand(carBrand);
        carAdvert.setCarModel(carModel);
        carAdvert.setCarModelGeneration(carModelGeneration);
        mapAdvertRequestToCarAdvert(carAdvert, advertRequest);
        mapCarBody(carBody, advertRequest.getCarBodyDTO());
        mapCarEngine(carEngine, advertRequest.getCarEngineDTO());
        mapCarOptions(carOptions, advertRequest.getCarOptionsDTO());

        carAdvert = carAdvertRepository.save(carAdvert);
        carAdvertRepository.flush();

        carBody.setCarAdvert(carAdvert);
        carEngine.setCarAdvert(carAdvert);
        carOptions.setCarAdvert(carAdvert);

        carBody = carBodyRepository.save(carBody);
        carBodyRepository.flush();
        carEngine = carEngineRepository.save(carEngine);
        carEngineRepository.flush();
        carOptions = carOptionsRepository.save(carOptions);
        carOptionsRepository.flush();

        carAdvert.setCarBody(carBody);
        carAdvert.setCarEngine(carEngine);
        carAdvert.setCarOptions(carOptions);

        carAdvertRepository.save(carAdvert);
    }

    private void mapAdvertRequestToCarAdvert(CarAdvert carAdvert, AdvertRequest advertRequest) {
        carAdvert.setPrice(advertRequest.getPrice());
        carAdvert.setYear(advertRequest.getYear());
        carAdvert.setFuelType(FuelType.valueOf(advertRequest.getFuelType()));
        carAdvert.setCountry(advertRequest.getCountry());
        carAdvert.setDescription(advertRequest.getDescription());
    }

    private void mapCarBody(CarBody carBody, CarBodyDTO carBodyDTO) {
        carBody.setBodyType(BodyType.valueOf(carBodyDTO.getBodyType()));
        carBody.setColorType(ColorType.valueOf(carBodyDTO.getColorType()));
        carBody.setNrOfSeats(carBodyDTO.getNrOfSeats());
    }

    private void mapCarEngine(CarEngine carEngine, CarEngineDTO carEngineDTO) {
        carEngine.setEmissionLevelType(EmissionLevelType.valueOf(carEngineDTO.getEmissionLevelType()));
        carEngine.setHorsePower(carEngineDTO.getHorsePower());
        carEngine.setCylinderCapacity(carEngineDTO.getCylinderCapacity());
        carEngine.setTransmissionType(TransmissionType.valueOf(carEngineDTO.getTransmissionType()));
        carEngine.setDrivetrain(DrivetrainType.valueOf(carEngineDTO.getDrivetrain()));
    }

    private void mapCarOptions(CarOptions carOptions, CarOptionsDTO carOptionsDTO) {
        carOptions.setHasAppleCarPlay(carOptionsDTO.isHasAppleCarPlay());
        carOptions.setHasHeadUpDisplay(carOptionsDTO.isHasHeadUpDisplay());
        carOptions.setHasAndroid(carOptionsDTO.isHasAndroid());
        carOptions.setHasBluetooth(carOptionsDTO.isHasBluetooth());
        carOptions.setHasAirConditioning(carOptionsDTO.isHasAirConditioning());
        carOptions.setHasDualClimatic(carOptionsDTO.isHasDualClimatic());
        carOptions.setHasPanoramicRoof(carOptionsDTO.isHasPanoramicRoof());
        carOptions.setHasSunRoof(carOptionsDTO.isHasSunRoof());
        carOptions.setUpholsteryType(UpholsteryType.valueOf(carOptionsDTO.getUpholsteryType()));
        carOptions.setHasElectricDriverSeat(carOptionsDTO.isHasElectricDriverSeat());
        carOptions.setHasElectricPassengerSeat(carOptionsDTO.isHasElectricPassengerSeat());
        carOptions.setHasElectricSeats(carOptionsDTO.isHasElectricSeats());
        carOptions.setHasHeatedDriverSeat(carOptionsDTO.isHasHeatedDriverSeat());
        carOptions.setHasHeatedPassengerSeat(carOptionsDTO.isHasHeatedPassengerSeat());
        carOptions.setHasHeatedSteeringWheel(carOptionsDTO.isHasHeatedSteeringWheel());
        carOptions.setHasHeatedWindscreen(carOptionsDTO.isHasHeatedWindscreen());
        carOptions.setHasCruiseControl(carOptionsDTO.isHasCruiseControl());
        carOptions.setHasDistanceControl(carOptionsDTO.isHasDistanceControl());
        carOptions.setHas360Cameras(carOptionsDTO.isHas360Cameras());
        carOptions.setHasParkingSensors(carOptionsDTO.isHasParkingSensors());
        carOptions.setHeadlightsType(HeadlightsType.valueOf(carOptionsDTO.getHeadlightsType()));
    }
}
