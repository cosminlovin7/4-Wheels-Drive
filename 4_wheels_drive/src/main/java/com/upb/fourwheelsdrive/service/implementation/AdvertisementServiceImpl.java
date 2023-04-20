package com.upb.fourwheelsdrive.service.implementation;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.car_advertisement.*;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.*;
import com.upb.fourwheelsdrive.model.car_advertisement.enums.*;
import com.upb.fourwheelsdrive.model.photo_storage.Photo;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.repository.*;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.service.JwtService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    private CarAdvertRepository carAdvertRepository;
    private CarBodyRepository carBodyRepository;
    private CarEngineRepository carEngineRepository;
    private CarOptionsRepository carOptionsRepository;
    private CarModelRepository carModelRepository;
    private CarModelGenerationRepository carModelGenerationRepository;
    private CarBrandRepository carBrandRepository;
    private ApplicationUserRepository applicationUserRepository;
    private PhotoRepository photoRepository;

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

        CarModelGeneration carModelGeneration = carModelGenerationRepository.findCarModelGenerationByGenerationNameAndCarModel(
                advertRequest.getGenerationName(), carModel).orElseThrow(() ->
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

        List<String> photosData = advertRequest.getPhotosData();

        for (String photoData : photosData) {
            Photo newPhoto = new Photo(photoData, carAdvert);

            photoRepository.save(newPhoto);
        }
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

        CarModelGeneration carModelGeneration = carModelGenerationRepository.findCarModelGenerationByGenerationNameAndCarModel(
                advertRequest.getGenerationName(), carModel).orElseThrow(() ->
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

    @Override
    public List<CarAdvertDTO> getCarAdverts(String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);
        List<CarAdvert> carAdverts = carAdvertRepository.getCarAdvertsByUserId(applicationUser.getId());

        return mapCarAdvertListToCarAdvertDTOList(carAdverts);
    }

    @Override
    public List<CarAdvertDTO> getAllCarAdverts() {
        List<CarAdvert> carAdverts = carAdvertRepository.findAll();

        return mapCarAdvertListToCarAdvertDTOList(carAdverts);
    }

    @Override
    public void addAdvertToFavorites(FavoriteAdRequest favoriteAdRequest, String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

        CarAdvert carAdvert = carAdvertRepository.findById(favoriteAdRequest.getCarAdvertisementId()).orElseThrow(() ->
                new BaseException(Constants.CAR_ADVERT_MISSING, HttpStatus.BAD_REQUEST)
        );

        if (carAdvert.getUserList().contains(applicationUser)) {
            throw new BaseException(Constants.AD_ALREADY_MARKED_AS_FAVORITE, HttpStatus.CONFLICT);
        }

        carAdvert.getUserList().add(applicationUser);
        carAdvertRepository.save(carAdvert);
    }

    @Override
    public void removeAdvertFromFavorites(FavoriteAdRequest favoriteAdRequest, String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

        CarAdvert carAdvert = carAdvertRepository.findById(favoriteAdRequest.getCarAdvertisementId()).orElseThrow(() ->
                new BaseException(Constants.CAR_ADVERT_MISSING, HttpStatus.BAD_REQUEST)
        );

        if (!carAdvert.getUserList().contains(applicationUser)) {
            throw new BaseException(Constants.AD_NOT_MARKED_AS_FAVORITE, HttpStatus.BAD_REQUEST);
        }

        carAdvert.getUserList().remove(applicationUser);
        carAdvertRepository.save(carAdvert);
    }

    @Override
    public List<CarAdvertDTO> getUserFavoriteAds(String jwtToken) {
        String username = jwtServiceImpl.getUsernameFromToken(jwtToken);
        ApplicationUser applicationUser = (ApplicationUser)applicationUserServiceImpl.loadUserByUsername(username);

        List<CarAdvert> favoriteCarAdverts = applicationUser.getCarAdverts();

        return mapCarAdvertListToCarAdvertDTOList(favoriteCarAdverts);
    }

    @Override
    public List<CarAdvertDTO> getFilteredCarAdverts(FilterParamsRequest filterParamsRequest) {
        List<CarAdvert> carAdverts = carAdvertRepository.getFilteredCarAdverts(
                filterParamsRequest.getBrandId(),
                filterParamsRequest.getModelId(),
                filterParamsRequest.getGenerationId(),
                filterParamsRequest.getMinYear(),
                filterParamsRequest.getMaxYear(),
                filterParamsRequest.getMinPrice(),
                filterParamsRequest.getMaxPrice()
        );

        return mapCarAdvertListToCarAdvertDTOList(carAdverts);
    }

    private List<CarAdvertDTO> mapCarAdvertListToCarAdvertDTOList(List<CarAdvert> carAdverts) {
        List<CarAdvertDTO>  carAdvertDTOList = new ArrayList<>();

        for (CarAdvert carAdvert : carAdverts) {
            CarAdvertDTO carAdvertDTO = new CarAdvertDTO();
            carAdvertDTO.setId(carAdvert.getId());
            carAdvertDTO.setBrand(carAdvert.getCarBrand().getBrandName());
            carAdvertDTO.setModel(carAdvert.getCarModel().getModelName());
            carAdvertDTO.setGeneration(carAdvert.getCarModelGeneration().getGenerationName());
            carAdvertDTO.setPrice(carAdvert.getPrice());
            carAdvertDTO.setYear(carAdvert.getYear());
            carAdvertDTO.setFuelType(carAdvert.getFuelType());
            carAdvertDTO.setCountry(carAdvert.getCountry());
            carAdvertDTO.setDescription(carAdvert.getDescription());

            CarBodyDTO carBodyDTO = new CarBodyDTO();
            mapCarBody(carAdvert.getCarBody(), carBodyDTO);

            CarEngineDTO carEngineDTO = new CarEngineDTO();
            mapCarEngine(carAdvert.getCarEngine(), carEngineDTO);

            CarOptionsDTO carOptionsDTO = new CarOptionsDTO();
            mapCarOptions(carAdvert.getCarOptions(), carOptionsDTO);

            carAdvertDTO.setCarBodyDTO(carBodyDTO);
            carAdvertDTO.setCarEngineDTO(carEngineDTO);
            carAdvertDTO.setCarOptionsDTO(carOptionsDTO);

            carAdvertDTOList.add(carAdvertDTO);
        }

        return carAdvertDTOList;
    }

    private void mapAdvertRequestToCarAdvert(CarAdvert carAdvert, AdvertRequest advertRequest) {
        carAdvert.setPrice(advertRequest.getPrice());
        carAdvert.setYear(advertRequest.getYear());
        carAdvert.setFuelType(FuelType.valueOf(advertRequest.getFuelType()));
        carAdvert.setCountry(advertRequest.getCountry());
        carAdvert.setDescription(advertRequest.getDescription());
    }

    private void mapCarBody(CarBody carBody, CarBodyDTO carBodyDTO) {
        if (carBodyDTO.getBodyType() != null)
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
