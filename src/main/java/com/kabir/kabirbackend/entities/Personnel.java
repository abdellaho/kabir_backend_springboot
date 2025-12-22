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
@Table(name = "personnel")
public class Personnel {
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
    @Column(name = "cin", nullable = false, length = 191)
    private String cin;

    @Size(max = 191)
    @NotNull
    @Column(name = "login", nullable = false, length = 191)
    private String login;

    @Size(max = 191)
    @NotNull
    @Column(name = "password", nullable = false, length = 191)
    private String password;

    @NotNull
    @Column(name = "typePersonnel", nullable = false)
    private int typePersonnel;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatComptePersonnel", nullable = false)
    private boolean etatComptePersonnel = false;

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
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "dateEntrer", nullable = false)
    private LocalDate dateEntrer;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @Column(name = "salaire", nullable = false, precision = 10, scale = 2)
    private double salaire;

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
    @Column(name = "consulterStock", nullable = false)
    private boolean consulterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterStock", nullable = false)
    private boolean ajouterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifierStock", nullable = false)
    private boolean modifierStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimerStock", nullable = false)
    private boolean supprimerStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "consulterRepertoire", nullable = false)
    private boolean consulterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterRepertoire", nullable = false)
    private boolean ajouterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifierRepertoire", nullable = false)
    private boolean modifierRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimerRepertoire", nullable = false)
    private boolean supprimerRepertoire = false;
}