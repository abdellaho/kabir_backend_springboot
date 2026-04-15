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
    @Column(name = "designation", length = 191)
    private String designation;

    @Size(max = 191)
    @Column(name = "cin", length = 191)
    private String cin;

    @Size(max = 191)
    @Column(name = "login", length = 191)
    private String login;

    @Size(max = 191)
    @Column(name = "password", length = 191)
    private String password;

    @NotNull
    @Column(name = "typePersonnel", nullable = false)
    private int typePersonnel;

    @ColumnDefault("0")
    @Column(name = "etatComptePersonnel", nullable = false)
    private boolean etatComptePersonnel = false;

    @Size(max = 191)
    @Column(name = "tel1", length = 191)
    private String tel1;

    @Size(max = 191)
    @Column(name = "tel2", length = 191)
    private String tel2;

    @Size(max = 191)
    @Column(name = "adresse", length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @Column(name = "dateEntrer")
    private LocalDate dateEntrer;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @Column(name = "salaire", nullable = false, precision = 10)
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

    @Column(name = "consulterTransport", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean consulterTransport;
    @Column(name = "ajouterTransport", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean ajouterTransport;
    @Column(name = "modifierTransport", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean modifierTransport;
    @Column(name = "supprimerTransport", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean supprimerTransport;
    @Column(name = "consulterLivraison", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean consulterLivraison;
    @Column(name = "ajouterLivraison", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean ajouterLivraison;
    @Column(name = "modifierLivraison", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean modifierLivraison;
    @Column(name = "supprimerLivraison", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean supprimerLivraison;
    @Column(name = "consulterFacture", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean consulterFacture;
    @Column(name = "ajouterFacture", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean ajouterFacture;
    @Column(name = "modifierFacture", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean modifierFacture;
    @Column(name = "supprimerFacture", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean supprimerFacture;
    @Column(name = "consulterEntretien", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean consulterEntretien;
    @Column(name = "ajouterEntretien", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean ajouterEntretien;
    @Column(name = "modifierEntretien", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean modifierEntretien;
    @Column(name = "supprimerEntretien", columnDefinition = "BOOLEAN DEFAULT 0")
    boolean supprimerEntretien;
}