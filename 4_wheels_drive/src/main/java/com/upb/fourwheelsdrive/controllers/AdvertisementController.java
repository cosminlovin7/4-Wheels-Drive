package com.upb.fourwheelsdrive.controllers;

import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;
import com.upb.fourwheelsdrive.service.AdvertisementService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementServiceImpl;

    @PostMapping("/sell")
    public ResponseEntity<BaseResponse> addAdvertisement(@RequestBody AdvertRequest advertRequest) {
        log.info(advertRequest.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(
                HttpStatus.CREATED.value(),
                Constants.CAR_ADVERT_CREATED
            )
        );
    }
}
