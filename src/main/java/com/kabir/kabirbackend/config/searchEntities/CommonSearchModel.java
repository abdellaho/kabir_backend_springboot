package com.kabir.kabirbackend.config.searchEntities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonSearchModel {
    private boolean searchByDate;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long fournisseurId;
    private Long repertoireId;
    private Long personnelId;
    private Long operateurId;
    private boolean absence;
    private boolean matin;
    private boolean apresMidi;
    private int etatcheque;
    private boolean compteCaisse;
    private String numCheque;
    private String numRemise;
}
