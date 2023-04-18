package com.upb.fourwheelsdrive.model.car_advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetAdvertsResponse {
    private Integer status;
    @JsonProperty("carAdverts")
    private List<CarAdvertDTO> carAdvertDTOList;

    public GetAdvertsResponse() {}
    public GetAdvertsResponse(final Integer status, final List<CarAdvertDTO> carAdvertDTOList) {
        this.status = status;
        this.carAdvertDTOList = carAdvertDTOList;
    }
}
