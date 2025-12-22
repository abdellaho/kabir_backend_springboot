package com.kabir.kabirbackend.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Prime}
 */
@Data
public class PrimeDTO implements Serializable {
    Long id;
    double montant;
    double prime;
}