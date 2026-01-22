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
@Table(name = "det-achat-etranger")
public class DetAchatEtranger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Stock stock;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private AchatEtranger achatEtranger;

    private int qteAchat;
    private int qteStock;
    private double prixAchat;
}
