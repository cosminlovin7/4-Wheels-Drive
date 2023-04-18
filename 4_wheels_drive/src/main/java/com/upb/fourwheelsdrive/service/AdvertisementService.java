package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.car_advertisement.dto.CarAdvertDTO;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.AdvertRequest;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.FavoriteAdRequest;
import com.upb.fourwheelsdrive.model.car_advertisement.dto.FilterParamsRequest;

import java.util.List;

public interface AdvertisementService {
    void createCarAdvert(AdvertRequest advertRequest, String jwtToken);
    void editCarAdvert(AdvertRequest advertRequest, Long carAdvertId, String jwtToken);
    void deleteCarAdvert(Long carAdvertId, String jwtToken);
    List<CarAdvertDTO> getCarAdverts(String jwtToken);
    List<CarAdvertDTO> getAllCarAdverts();
    void addAdvertToFavorites(FavoriteAdRequest favoriteAdRequest, String jwtToken);
    List<CarAdvertDTO> getUserFavoriteAds(String jwtToken);
    void removeAdvertFromFavorites(FavoriteAdRequest favoriteAdRequest, String jwtToken);
    List<CarAdvertDTO> getFilteredCarAdverts(FilterParamsRequest filterParamsRequest);
}
