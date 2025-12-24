package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fournisseur")
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @NotNull
    @Column(name = "type", nullable = false)
    private int type;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel1", nullable = false, length = 191)
    private String tel1;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel2", nullable = false, length = 191)
    private String tel2;

    @Size(max = 191)
    @NotNull
    @Column(name = "ice", nullable = false, length = 191)
    private String ice;

    @Size(max = 191)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimer", nullable = false)
    private boolean supprimer = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "nbr_operation_client", nullable = false)
    private int nbrOperationClient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "villeId")
    private Ville ville;
}