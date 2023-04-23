package com.upb.fourwheelsdrive.model.admin_control;

import lombok.Data;

@Data
public class CarModelDTO {
    private Long id;
    private String modelName;
    private Long brandId;
    private String brandName;

    public CarModelDTO() {}

    public CarModelDTO(final Long id, final String modelName, final Long brandId, final String brandName) {
        this.id = id;
        this.modelName = modelName;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public CarModelDTO(final Long id, final String modelName) {
        this.id = id;
        this.modelName = modelName;
    }
}
