package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "repertoire")
public class Repertoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

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
    @Column(name = "tel3", nullable = false, length = 191)
    private String tel3;

    @Size(max = 191)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "typeRepertoire", nullable = false)
    private int typeRepertoire;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private int typeReglment;

    @Size(max = 191)
    @NotNull
    @Column(name = "ife", nullable = false, length = 191)
    private String ife;

    @Size(max = 191)
    @NotNull
    @Column(name = "ice", nullable = false, length = 191)
    private String ice;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "bloquer", nullable = false)
    private boolean bloquer = false;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @Size(max = 191)
    @NotNull
    @Column(name = "observation", nullable = false, length = 191)
    private String observation;

    @NotNull
    @Column(name = "nbrOperationClient", nullable = false)
    private int nbrOperationClient;

    @NotNull
    @Column(name = "plafond", nullable = false, precision = 10)
    private double plafond;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnelId")
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "villeId")
    private Ville ville;

}