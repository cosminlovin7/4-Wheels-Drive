package com.upb.fourwheelsdrive.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class JwtUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getAuthorityFromToken(String jwtToken) throws JsonProcessingException {
        String[] parts = jwtToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1])); //payload
        JsonNode claims = objectMapper.readTree(payload);
        JsonNode authorities = claims.get("authorities");
        String authority = authorities.get(0).get("authority").asText();

        return authority;
    }
}
