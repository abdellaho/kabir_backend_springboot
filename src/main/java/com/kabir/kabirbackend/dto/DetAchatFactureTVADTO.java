package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatFactureTVA}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetAchatFactureTVADTO implements Serializable {
    private Long id;
    private double taux;
    private double mntHT;
    private double mntTVA;
    private double mntTTC;
    private Long achatFactureId;
}