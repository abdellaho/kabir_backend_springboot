package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detbulttinpai")
public class DetBulttinPai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "prixlivraison", nullable = false, precision = 10)
    private double prixlivraison;

    @NotNull
    @Column(name = "qtevendu", nullable = false)
    private int qtevendu;

    @NotNull
    @Column(name = "prixvente", nullable = false, precision = 10)
    private double prixvente;

    @NotNull
    @Column(name = "remise", nullable = false, precision = 10)
    private double remise;

    @NotNull
    @Column(name = "mantantvendu", nullable = false, precision = 10)
    private double mantantvendu;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10)
    private double commission;

    @NotNull
    @Column(name = "commsiondh", nullable = false, precision = 10)
    private double commsiondh;

    @NotNull
    @Column(name = "mantant", nullable = false, precision = 10)
    private double mantant;

    @NotNull
    @Column(name = "benDH", nullable = false, precision = 10)
    private double benDH;

    @NotNull
    @Column(name = "commissionFixe", nullable = false, precision = 10)
    private double commissionFixe;

    @NotNull
    @Column(name = "prixCommercial", nullable = false, precision = 10)
    private double prixCommercial;

    @NotNull
    @Column(name = "primeCommercial", nullable = false, precision = 10)
    private double primeCommercial;

    @NotNull
    @Column(name = "primeProduit", nullable = false, precision = 10)
    private double primeProduit;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "avecZero", nullable = false)
    private boolean avecZero = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bulttinPaiId", nullable = false)
    private BulttinPai bulttinPai;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produitId", nullable = false)
    private Stock produit;

}