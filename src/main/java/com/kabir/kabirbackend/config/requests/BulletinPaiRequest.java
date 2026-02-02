package com.kabir.kabirbackend.config.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulletinPaiRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long commercialId;
    private boolean sansMontant;
    private Long livraisonId;
}
