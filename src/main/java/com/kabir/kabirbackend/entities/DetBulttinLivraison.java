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
@Table(name = "detbulttinlivraison")
public class DetBulttinLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10)
    private double commission;

    @NotNull
    @Column(name = "commissionFixe", nullable = false, precision = 10)
    private double commissionFixe;

    @NotNull
    @Column(name = "commsiondh", nullable = false, precision = 10)
    private double commsiondh;

    @NotNull
    @Column(name = "mantantcommission", nullable = false, precision = 10)
    private double mantantcommission;

    @NotNull
    @Column(name = "benDH", nullable = false, precision = 10)
    private double benDH;

    @NotNull
    @Column(name = "rougenormal", nullable = false)
    private boolean rougenormal = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bulttinPaiId", nullable = false)
    private BulttinPai bulttinPai;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livraisonId", nullable = false)
    private Livraison livraison;

}