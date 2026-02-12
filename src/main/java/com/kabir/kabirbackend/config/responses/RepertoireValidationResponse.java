package com.kabir.kabirbackend.config.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepertoireValidationResponse {

    private boolean exists;
    private Map<String, String> errors = new HashMap<>();
}
