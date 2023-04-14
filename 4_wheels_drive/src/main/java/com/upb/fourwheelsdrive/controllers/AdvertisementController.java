package com.upb.fourwheelsdrive.controllers;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementServiceImpl;

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
        ));
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
        ));
    }

    private String getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BaseException(Constants.INVALID_AUTHENTICATION_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        return authHeader.substring(7);
    }
}
