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
@Table(name = "det_stock_depot")
public class DetStockDepot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qte", nullable = false)
    private int qte;

    @NotNull
    @Column(name = "prix_vente", nullable = false)
    private double prixVente;

    @NotNull
    @Column(name = "unite_gratuit", nullable = false)
    private int uniteGratuit;

    @NotNull
    @Column(name = "remise", nullable = false)
    private double remise;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stockDepotId", nullable = false)
    private StockDepot stockDepot;

}
