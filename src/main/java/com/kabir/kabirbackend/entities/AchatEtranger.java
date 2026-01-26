package com.kabir.kabirbackend.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achat-etranger")
public class AchatEtranger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Fournisseur fournisseur;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Personnel operateur;
    private String codeFacture;
    private int numFacture;
    private LocalDate dateFacture;
    private Instant sysDate;
    private LocalDate dateAvances1;
    private double mantantAvancs1;
    private LocalDate dateAvances2;
    private double mantantAvancs2;
    private double totalPaye;
    private String mntFacture;
    private double mntDouane;
    private double mntTransport;
    private double mntTransportIntern;
    private double mntTransit;
    private double mntMagasinage;
    private double prixAchatDetaille;
    private double totalAllMnt;
}
