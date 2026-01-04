package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.PersonnelDTO;

public record LoginResponse(String token, String refreshToken, Integer expiresIn, PersonnelDTO user) {
}
