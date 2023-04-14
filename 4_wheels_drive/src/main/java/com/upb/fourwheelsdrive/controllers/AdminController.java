package com.upb.fourwheelsdrive.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.admin_control.BrandNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelGenerationNamesDTO;
import com.upb.fourwheelsdrive.model.admin_control.ModelNamesDTO;
import com.upb.fourwheelsdrive.service.AdminService;
import com.upb.fourwheelsdrive.utils.Constants;
import com.upb.fourwheelsdrive.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/admin/control")
@RestController
public class AdminController {
    private AdminService adminServiceImpl;

    @GetMapping("/brands")
    public ResponseEntity<BrandNamesDTO> getBrands(
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        BrandNamesDTO brandNamesDTO = adminServiceImpl.getBrands();

        return ResponseEntity.status(HttpStatus.OK).body(brandNamesDTO);
    }

    @GetMapping("/models")
    public ResponseEntity<ModelNamesDTO> getModels(
            @RequestParam("brand") String brandName,
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        ModelNamesDTO modelNamesDTO = adminServiceImpl.getModels(brandName);

        return ResponseEntity.status(HttpStatus.OK).body(modelNamesDTO);
    }

    @GetMapping("/generations")
    public ResponseEntity<ModelGenerationNamesDTO> getModels(
            @RequestParam("brand") String brandName,
            @RequestParam("model") String modelName,
            @NonNull HttpServletRequest request
    ) {
        checkIfUserIsAdmin(request);

        ModelGenerationNamesDTO modelGenerationNamesDTO = adminServiceImpl.getModelGenerations(brandName, modelName);

        return ResponseEntity.status(HttpStatus.OK).body(modelGenerationNamesDTO);
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
