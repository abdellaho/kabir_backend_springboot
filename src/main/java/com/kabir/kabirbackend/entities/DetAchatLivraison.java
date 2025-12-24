package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detachatlivraison")
public class DetAchatLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "unitegratuit", nullable = false)
    private int unitegratuit;

    @NotNull
    @Column(name = "qteacheter", nullable = false)
    private int qteacheter;

    @NotNull
    @Column(name = "prixAchatHt", nullable = false, precision = 10)
    private double prixAchatHt;

    @NotNull
    @Column(name = "prixAchatTtc", nullable = false, precision = 10)
    private double prixAchatTtc;

    @NotNull
    @Column(name = "remiseAchat", nullable = false, precision = 10)
    private double remiseAchat;

    @NotNull
    @Column(name = "prixVenteAchatHT", nullable = false, precision = 10)
    private double prixVenteAchatHT;

    @NotNull
    @Column(name = "prixVenteTtc", nullable = false, precision = 10)
    private double prixVenteTtc;

    @NotNull
    @Column(name = "mantantHt", nullable = false, precision = 10)
    private double mantantHt;

    @NotNull
    @Column(name = "mantantTTC", nullable = false, precision = 10)
    private double mantantTTC;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10)
    private double tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10)
    private double tva20;

    @NotNull
    @Column(name = "tva14", nullable = false, precision = 10)
    private double tva14;

    @NotNull
    @Column(name = "tva10", nullable = false, precision = 10)
    private double tva10;

    @NotNull
    @Column(name = "beneficeDH", nullable = false, precision = 10)
    private double beneficeDH;

    @NotNull
    @Column(name = "benepourcentage", nullable = false, precision = 10)
    private double benepourcentage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "achatLivraisonId", nullable = false)
    private AchatLivraison achatLivraison;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

}