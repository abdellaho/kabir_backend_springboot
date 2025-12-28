package com.kabir.kabirbackend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "det_achat_simple")
public class DetAchatSimple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int qte;

    @Column(name = "prix_vente")
    private double prixVente;

    private double remise;
    private int uniteGratuite;
    private double montant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achat_simple", nullable = false)
    private AchatSimple achatSimple;
}
