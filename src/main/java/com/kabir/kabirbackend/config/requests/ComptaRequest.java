package com.kabir.kabirbackend.config.requests;

import java.time.LocalDate;

public record ComptaRequest(LocalDate dateDebut, LocalDate dateFin) {
}
