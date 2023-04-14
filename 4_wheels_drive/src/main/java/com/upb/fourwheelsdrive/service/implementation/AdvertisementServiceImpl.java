package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.car_advertisement.*;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarBodyDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarEngineDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarOptionsDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.*;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.repository.*;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.service.JwtService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private JwtService jwtServiceImpl;
    private UserDetailsService applicationUserServiceImpl;

    @Transactional
    @Override
    public void createCarAdvert(AdvertRequest advertRequest, String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

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
        if (advertRequest.getCarBodyDTO() != null)
            mapCarBody(carBody, advertRequest.getCarBodyDTO());
        if (advertRequest.getCarEngineDTO() != null)
            mapCarEngine(carEngine, advertRequest.getCarEngineDTO());
        if (advertRequest.getCarOptionsDTO() != null)
            mapCarOptions(carOptions, advertRequest.getCarOptionsDTO());
        carAdvert.setApplicationUser(applicationUser);

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

    @Transactional
    @Override
    public void editCarAdvert(AdvertRequest advertRequest, Long carAdvertId, String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

        CarAdvert carAdvert = carAdvertRepository.findById(carAdvertId).orElseThrow(() ->
                new BaseException(Constants.CAR_ADVERT_MISSING, HttpStatus.BAD_REQUEST)
        );

        if (applicationUser.getId() != carAdvert.getApplicationUser().getId()) {
            throw new BaseException(Constants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }

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

        CarBody editedCarBody = carAdvert.getCarBody();
        CarEngine editedCarEngine = carAdvert.getCarEngine();
        CarOptions editedCarOptions = carAdvert.getCarOptions();

        mapAdvertRequestToCarAdvert(carAdvert, advertRequest);
        if (advertRequest.getCarBodyDTO() != null)
            mapCarBody(editedCarBody, advertRequest.getCarBodyDTO());
        if (advertRequest.getCarEngineDTO() != null)
            mapCarEngine(editedCarEngine, advertRequest.getCarEngineDTO());
        if (advertRequest.getCarOptionsDTO() != null)
            mapCarOptions(editedCarOptions, advertRequest.getCarOptionsDTO());
        carAdvert.setCarBrand(carBrand);
        carAdvert.setCarModel(carModel);
        carAdvert.setCarModelGeneration(carModelGeneration);

        carBodyRepository.save(editedCarBody);
        carEngineRepository.save(editedCarEngine);
        carOptionsRepository.save(editedCarOptions);
        carAdvertRepository.save(carAdvert);
    }

    @Transactional
    @Override
    public void deleteCarAdvert(Long carAdvertId, String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);

        CarAdvert carAdvert = carAdvertRepository.findById(carAdvertId).orElseThrow(() ->
            new BaseException(Constants.CAR_ADVERT_MISSING, HttpStatus.BAD_REQUEST)
        );

        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

        if (carAdvert.getApplicationUser().getId() != applicationUser.getId()) {
            throw new BaseException(Constants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }

        carAdvertRepository.delete(carAdvert);
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
        if (carBodyDTO.getColorType() != null)
            carBody.setColorType(ColorType.valueOf(carBodyDTO.getColorType()));
        carBody.setNrOfSeats(carBodyDTO.getNrOfSeats());
    }

    private void mapCarEngine(CarEngine carEngine, CarEngineDTO carEngineDTO) {
        if (carEngineDTO.getEmissionLevelType() != null)
            carEngine.setEmissionLevelType(EmissionLevelType.valueOf(carEngineDTO.getEmissionLevelType()));
        carEngine.setHorsePower(carEngineDTO.getHorsePower());
        carEngine.setCylinderCapacity(carEngineDTO.getCylinderCapacity());
        if (carEngineDTO.getEmissionLevelType() != null)
            carEngine.setTransmissionType(TransmissionType.valueOf(carEngineDTO.getTransmissionType()));
        if (carEngineDTO.getDrivetrain() != null)
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
        if (carOptionsDTO.getUpholsteryType() != null)
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
        if (carOptionsDTO.getHeadlightsType() != null)
            carOptions.setHeadlightsType(HeadlightsType.valueOf(carOptionsDTO.getHeadlightsType()));
    }
}
