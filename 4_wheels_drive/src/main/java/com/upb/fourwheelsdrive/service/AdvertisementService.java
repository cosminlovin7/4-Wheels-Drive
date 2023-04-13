package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;

public interface AdvertisementService {
    void createCarAdvert(AdvertRequest advertRequest);
}
