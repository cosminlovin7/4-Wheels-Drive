package com.upb.fourwheelsdrive.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.admin_control.*;
import com.upb.fourwheelsdrive.service.AdminService;
import com.upb.fourwheelsdrive.utils.Constants;
import com.upb.fourwheelsdrive.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/admin/control")
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private AdminService adminServiceImpl;

    @GetMapping("/brands")
    public ResponseEntity<BrandNamesDTO> getBrands(
            @NonNull HttpServletRequest request
    ) {
        BrandNamesDTO brandNamesDTO = adminServiceImpl.getBrands();

        return ResponseEntity.status(HttpStatus.OK).body(brandNamesDTO);
    }

    @GetMapping("/models")
    public ResponseEntity<ModelNamesDTO> getModels(
            @RequestParam("brand") String brandName,
            @NonNull HttpServletRequest request
    ) {
        ModelNamesDTO modelNamesDTO = adminServiceImpl.getModels(brandName);

        return ResponseEntity.status(HttpStatus.OK).body(modelNamesDTO);
    }

    @GetMapping("/models/all")
    public ResponseEntity<List<CarModelDTO>> getAllModels() {
        List<CarModelDTO> modelNamesDTO = adminServiceImpl.getAllModels();

        return ResponseEntity.status(HttpStatus.OK).body(modelNamesDTO);
    }

    @GetMapping("/generations")
    public ResponseEntity<ModelGenerationNamesDTO> getModels(
            @RequestParam("brand") String brandName,
            @RequestParam("model") String modelName,
            @NonNull HttpServletRequest request
    ) {
        ModelGenerationNamesDTO modelGenerationNamesDTO = adminServiceImpl.getModelGenerations(brandName, modelName);

        return ResponseEntity.status(HttpStatus.OK).body(modelGenerationNamesDTO);
    }

    @GetMapping("/generations/all")
    public ResponseEntity<List<CarGenerationDTO>> getAllGenerations() {
        List<CarGenerationDTO> carGenerationDTOList = adminServiceImpl.getAllGenerations();

        return ResponseEntity.status(HttpStatus.OK).body(carGenerationDTOList);
    }

    @PostMapping("/add/brand")
    public ResponseEntity<BaseResponse> addBrand(
            @RequestBody RequestBrandDTO requestBrandDTO,
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        log.info(requestBrandDTO.toString());
        adminServiceImpl.addBrand(requestBrandDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(
                HttpStatus.OK.value(),
                Constants.BRAND_ADDED
            )
        );
    }

    @PostMapping("/add/model")
    public ResponseEntity<BaseResponse> addBrand(
            @RequestBody RequestModelDTO requestModelDTO,
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        adminServiceImpl.addModel(requestModelDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(
                        HttpStatus.OK.value(),
                        Constants.MODEL_ADDED
                )
        );
    }

    @PostMapping("/add/generation")
    public ResponseEntity<BaseResponse> addBrand(
            @RequestBody RequestModelGenerationDTO requestModelGenerationDTO,
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        adminServiceImpl.addModelGeneration(requestModelGenerationDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(
                        HttpStatus.OK.value(),
                        Constants.GENERATION_ADDED
                )
        );
    }

    private void checkIfUserIsAdmin(HttpServletRequest request) {
        String userAuthorization;

        try {
            userAuthorization = JwtUtils.getAuthorityFromToken(getJwtToken(request));
        } catch (JsonProcessingException e) {
            throw new BaseException(Constants.PARSE_JSON_ERROR, HttpStatus.BAD_REQUEST);
        }

        if (!userAuthorization.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            throw new BaseException(Constants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BaseException(Constants.INVALID_AUTHENTICATION_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        return authHeader.substring(7);
    }
}
