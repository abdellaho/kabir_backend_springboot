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
@Table(name = "det_achat_facture_tva")
public class DetAchatFactureTVA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double taux;
    private double mntHT;
    private double mntTVA;
    private double mntTTC;

    @ManyToOne(fetch = FetchType.LAZY)
    private AchatFacture achatFacture;
}
