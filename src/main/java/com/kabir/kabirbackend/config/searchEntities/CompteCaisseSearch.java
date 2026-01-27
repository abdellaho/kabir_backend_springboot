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
public class CompteCaisseSearch {
    private boolean compteCaisse;
    private boolean searchByDate;
    private LocalDate dateDebutSearch;
    private LocalDate dateFinSearch;
}
