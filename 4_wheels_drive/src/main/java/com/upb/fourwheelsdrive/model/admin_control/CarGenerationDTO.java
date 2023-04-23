package com.upb.fourwheelsdrive.model.admin_control;

import lombok.Data;

@Data
public class CarGenerationDTO {
    private Long id;
    private String generationName;
    private Long modelId;
    private String modelName;

    public CarGenerationDTO() {}

    public CarGenerationDTO(final Long id, final String generationName) {
        this.id = id;
        this.generationName = generationName;
    }

    public CarGenerationDTO(final Long id, final String generationName, final Long modelId, final String modelName) {
        this.id = id;
        this.generationName = generationName;
        this.modelId = modelId;
        this.modelName = modelName;
    }
}
