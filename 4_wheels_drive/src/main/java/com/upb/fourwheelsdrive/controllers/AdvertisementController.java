package com.upb.fourwheelsdrive.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.*;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementServiceImpl;
    private final ObjectMapper objectMapper;

    @PostMapping("/sell")
    public ResponseEntity<BaseResponse> addAdvertisement(
            @RequestBody AdvertRequest advertRequest,
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        log.info(advertRequest.toString());
        advertisementServiceImpl.createCarAdvert(advertRequest, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(
                HttpStatus.CREATED.value(),
                Constants.CAR_ADVERT_CREATED
            )
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<BaseResponse> editAdvertisement(
            @RequestBody AdvertRequest advertRequest,
            @RequestParam("car_advert_id") Long carAdvertId,
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        log.info(advertRequest.toString());

        advertisementServiceImpl.editCarAdvert(advertRequest, carAdvertId, jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(
                HttpStatus.OK.value(),
                Constants.CAR_ADVERT_EDITED
            )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteAdvertisement(
            @RequestParam("car_advert_id") Long carAdvertId,
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        advertisementServiceImpl.deleteCarAdvert(carAdvertId, jwtToken);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse(
                HttpStatus.NO_CONTENT.value(),
                Constants.CAR_ADVERT_DELETED
            )
        );
    }

    @GetMapping("/adverts")
    public ResponseEntity<GetAdvertsResponse> getCarAdverts(
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        List<CarAdvertDTO> carAdvertDTOList = advertisementServiceImpl.getCarAdverts(jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(new GetAdvertsResponse(
                HttpStatus.OK.value(),
                carAdvertDTOList
            )
        );
    }

    @GetMapping()
    public ResponseEntity<GetAdvertsResponse> getAllCarAdverts() {
        List<CarAdvertDTO> carAdvertDTOList = advertisementServiceImpl.getAllCarAdverts();

        return ResponseEntity.status(HttpStatus.OK).body(new GetAdvertsResponse(
                HttpStatus.OK.value(),
                carAdvertDTOList
            )
        );
    }

    @PostMapping("/add/favorite")
    public ResponseEntity<BaseResponse> addAdvertToFavorites(
            @RequestBody FavoriteAdRequest favoriteAdRequest,
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        log.info(favoriteAdRequest.toString());

        advertisementServiceImpl.addAdvertToFavorites(favoriteAdRequest, jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(
                HttpStatus.OK.value(),
                Constants.ADVERT_ADDED_FAVORITE
            )
        );
    }

    @PostMapping("/delete/favorite")
    public ResponseEntity<BaseResponse> removeAdvertFromFavorites(
            @RequestBody FavoriteAdRequest favoriteAdRequest,
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        advertisementServiceImpl.removeAdvertFromFavorites(favoriteAdRequest, jwtToken);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse(
                HttpStatus.NO_CONTENT.value(),
                ""
            )
        );
    }

    @GetMapping("/favorites")
    public ResponseEntity<GetAdvertsResponse> getFavoriteCarAdverts(
            @NonNull HttpServletRequest request
    ) {
        String jwtToken = getJwtToken(request);

        List<CarAdvertDTO> carAdvertDTOList = advertisementServiceImpl.getUserFavoriteAds(jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(new GetAdvertsResponse(
                HttpStatus.OK.value(),
                carAdvertDTOList
            )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<GetAdvertsResponse> getFilteredCarAdverts(
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "modelId", required = false) Long modelId,
            @RequestParam(value = "generationId", required = false) Long generationId,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "minYear", required = false) Integer minYear,
            @RequestParam(value = "maxYear", required = false) Integer maxYear,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "fuelType", required = false) String fuelType,
            @RequestParam(value = "country", required = false) String country
    ) {
        FilterParamsRequest filterParamsRequest = new FilterParamsRequest(
                brandId,
                modelId,
                generationId,
                year,
                minYear,
                maxYear,
                price,
                minPrice,
                maxPrice,
                fuelType,
                country
        );

        log.info(filterParamsRequest.toString());

        List<CarAdvertDTO> carAdvertDTOList = advertisementServiceImpl.getFilteredCarAdverts(filterParamsRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new GetAdvertsResponse(
                HttpStatus.OK.value(),
                carAdvertDTOList
            )
        );
    }

    private String getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BaseException(Constants.INVALID_AUTHENTICATION_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        return authHeader.substring(7);
    }
}
