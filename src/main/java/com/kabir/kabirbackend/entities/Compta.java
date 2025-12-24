package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compta")
public class Compta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateDebut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "dateFin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "montantTVAPrecedent", nullable = false, precision = 10)
    private double montantTVAPrecedent;

    @NotNull
    @Column(name = "montantTVAAchat", nullable = false, precision = 10)
    private double montantTVAAchat;

    @NotNull
    @Column(name = "montantTVAVente", nullable = false, precision = 10)
    private double montantTVAVente;

    @NotNull
    @Column(name = "resutMnt", nullable = false, precision = 10)
    private double resutMnt;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

}