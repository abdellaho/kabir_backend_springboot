package com.kabir.kabirbackend.config.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComptaResponse {
    private Double tva7Sum;
    private Double tva20Sum;
    private Double achatTvaSum;
}
