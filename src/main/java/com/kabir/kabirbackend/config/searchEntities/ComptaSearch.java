package com.kabir.kabirbackend.config.searchEntities;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComptaSearch {
    private Long id;
    private boolean searchByDate;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montantTVAPrecedent;
    private double montantTVAAchat;
    private double montantTVAVente;
    private double resutMnt;
    private Long repertoireId;
}
